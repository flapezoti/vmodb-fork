package dk.ku.di.dms.vms.sdk.embed.client;

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
