package dk.ku.di.dms.vms.sdk.embed.client;

import dk.ku.di.dms.vms.modb.api.query.statement.SelectStatement;
import dk.ku.di.dms.vms.modb.common.schema.VmsDataModel;
import dk.ku.di.dms.vms.modb.common.schema.network.node.VmsNode;
import dk.ku.di.dms.vms.modb.common.serdes.IVmsSerdesProxy;
import dk.ku.di.dms.vms.modb.common.serdes.VmsSerdesProxyBuilder;
import dk.ku.di.dms.vms.modb.definition.Table;
import dk.ku.di.dms.vms.modb.transaction.TransactionManager;
import dk.ku.di.dms.vms.sdk.core.metadata.VmsMetadataLoader;
import dk.ku.di.dms.vms.sdk.core.metadata.VmsRuntimeMetadata;
import dk.ku.di.dms.vms.sdk.core.scheduler.VmsTransactionScheduler;
import dk.ku.di.dms.vms.sdk.embed.channel.VmsEmbedInternalChannels;
import dk.ku.di.dms.vms.sdk.embed.handler.VmsEventHandler;
import dk.ku.di.dms.vms.sdk.embed.metadata.EmbedMetadataLoader;
import dk.ku.di.dms.vms.web_common.IHttpHandler;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

/**
 * Result of VmsApplication.prepare(options): the catalog, storage, and repository proxies for
 * this VMS have been loaded, but no @Microservice instance has been constructed yet.
 *
 * This is the extension point for "deep" dependency-injection integrations: a host framework
 * (e.g. Spring) can call getRepositoryProxy(table) to obtain the same repository instances
 * VmsApplication#getRepositoryProxy(table) would expose after a full build, use them to
 * construct its own, framework-managed @Microservice bean(s) -- real constructor injection,
 * full bean lifecycle -- and then call complete(...) with those instances to finish building
 * the VmsApplication exactly as VmsApplication.build(...) would have, minus VMODB constructing
 * the service itself via reflection.
 */
public final class VmsPreparedApplication {

    private final VmsApplicationOptions options;
    private final String packageName;
    private final Reflections reflections;
    private final Set<Class<?>> vmsClasses;
    private final Map<String, VmsDataModel> vmsDataModelMap;
    private final String vmsName;
    private final Map<String, Table> catalog;
    private final TransactionManager transactionManager;
    private final Map<String, Object> tableToRepositoryMap;

    VmsPreparedApplication(VmsApplicationOptions options, String packageName, Reflections reflections,
                           Set<Class<?>> vmsClasses, Map<String, VmsDataModel> vmsDataModelMap, String vmsName,
                           Map<String, Table> catalog, TransactionManager transactionManager,
                           Map<String, Object> tableToRepositoryMap) {
        this.options = options;
        this.packageName = packageName;
        this.reflections = reflections;
        this.vmsClasses = vmsClasses;
        this.vmsDataModelMap = vmsDataModelMap;
        this.vmsName = vmsName;
        this.catalog = catalog;
        this.transactionManager = transactionManager;
        this.tableToRepositoryMap = tableToRepositoryMap;
    }

    /** The VMS's own name, as declared in @Microservice("..."). */
    public String getVmsName() {
        return vmsName;
    }

    /** Same repository proxy VmsApplication#getRepositoryProxy(table) exposes after a full build. */
    public Object getRepositoryProxy(String table) {
        return tableToRepositoryMap.get(table);
    }

    /**
     * Builds an additional repository proxy for `table` and `repositoryInterface`, the same way
     * prepare()'s own repository proxies are built, but with `extraQueries` merged in -- a map
     * this class (and VMODB generally) never has to know the origin of. Written for a host
     * framework that wants its own query-defining annotation (not VMODB's @Query) recognized on
     * a repository interface: the framework reads its own annotation, parses each query string
     * with dk.ku.di.dms.vms.modb.api.query.parser.Parser itself, and passes the resulting
     * method-name-keyed map here.
     *
     * This exists specifically so neither OperationalAPI nor Table (VMODB's internal operations
     * on its own private state) ever has to leave this class: the caller gets back a finished
     * proxy, the same shape getRepositoryProxy(table) already returns, nothing lower-level.
     */
    public Object buildRepositoryProxy(String table, Class<?> repositoryInterface,
                                        Map<String, SelectStatement> extraQueries) throws Exception {
        return EmbedMetadataLoader.buildRepositoryProxy(
                table, repositoryInterface, catalog, transactionManager, extraQueries);
    }

    /**
     * Finishes building the VmsApplication using externally-constructed @Microservice instances
     * instead of VMODB's own reflective construction.
     *
     * vmsInstances must be keyed by each class's canonical name (Class#getName()) -- the same
     * convention VmsApplication#getService(String) already reads back out by -- with one entry
     * per @Microservice class found when prepare(...) scanned this package.
     */
    public VmsApplication complete(Map<String, Object> vmsInstances, HttpHandlerBuilder builder) throws Exception {
        VmsRuntimeMetadata vmsMetadata = VmsMetadataLoader.loadWithPreBuiltInstances(
                reflections, packageName, vmsClasses, vmsDataModelMap, vmsInstances, tableToRepositoryMap);

        IVmsSerdesProxy serdes = VmsSerdesProxyBuilder.build();

        // ideally lastTid and lastBatch must be read from the storage
        VmsNode vmsIdentifier = new VmsNode(
                options.host(), options.port(), vmsName,
                0, 0, 0,
                vmsMetadata.dataModel(),
                vmsMetadata.inputEventSchema(),
                vmsMetadata.outputEventSchema());

        IHttpHandler httpHandler = builder.build(transactionManager, tableToRepositoryMap::get);

        VmsEmbedInternalChannels vmsInternalPubSubService = new VmsEmbedInternalChannels();

        VmsEventHandler eventHandler = VmsEventHandler.build(vmsIdentifier, transactionManager, vmsInternalPubSubService, vmsMetadata, options, httpHandler, serdes);

        VmsTransactionScheduler transactionScheduler = VmsTransactionScheduler.build(
                vmsName,
                vmsInternalPubSubService.transactionInputQueue(),
                vmsMetadata.queueToVmsTransactionMap(),
                transactionManager,
                eventHandler::processOutputEvent,
                options.vmsThreadPoolSize());

        return new VmsApplication(vmsName, vmsMetadata, catalog, eventHandler, transactionManager, transactionScheduler, vmsInternalPubSubService);
    }
}
