package dk.ku.di.dms.vms.sdk.core.metadata;

import dk.ku.di.dms.vms.modb.common.schema.VmsDataModel;
import dk.ku.di.dms.vms.modb.common.schema.VmsEventSchema;

import java.util.Map;

/**
 * A data class that stores the mappings between events, queues, and transactions.
 * This record is built before the database tables are created, so no tables here.
 * Q: Why data schema is a map?
 * A: Potentially a server could hold two (or more) VMSs. But for now only one.
 * A catalog usually stores tables, views, columns, triggers and procedures in a DBMS
 * See <a href="https://en.wikipedia.org/wiki/Oracle_metadata">Oracle Metadata</a>
 * In our case, the table already stores the columns
 * Q: We don't have triggers nor stored procedures?
 * A: Actually we have mappings from events to application functions
 * For now, we don't have views, but we could implement the application-defined
 * queries as views and store them here
 */
public record VmsRuntimeMetadata(

        Map<String, VmsDataModel> dataModel,
        Map<String, VmsEventSchema> inputEventSchema,
        Map<String, VmsEventSchema> outputEventSchema,

        Map<String, VmsTransactionMetadata> queueToVmsTransactionMap,
        Map<String, Class<?>> queueToEventMap,
        Map<Class<?>, String> eventToQueueMap,

        Map<String, String> clazzNameToVmsName,

        // The classes annotated with {@link dk.ku.di.dms.vms.modb.api.annotations.Microservice}
        Map<String, Object> loadedVmsInstances,

        // key is the table (or entity) name
        Map<String, Object> repositoryProxyMap

){}