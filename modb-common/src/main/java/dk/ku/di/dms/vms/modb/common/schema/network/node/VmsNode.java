package dk.ku.di.dms.vms.modb.common.schema.network.node;

import dk.ku.di.dms.vms.modb.common.schema.VmsDataModel;
import dk.ku.di.dms.vms.modb.common.schema.VmsEventSchema;

import java.util.Map;

/**
 * The identification of a connecting DBMS daemon
 * ideally one vms per dbms proxy, but in the future may be many.
 * but who knows... the sdk are already sending a map...
 * I'm relying on the fact that VMSs do not switch name, host, and port
 * This class is supposed to be used by the coordinator to find about
 * producers and consumers and then form and send the consumer set
 * to each virtual microservice
 */
public final class VmsNode extends IdentifiableNode {

    /**
     * The batch offset, monotonically increasing.
     * to avoid vms to process transactions from the
     * next batch while the current has not finished yet
     */
    public long batch;

    // last tid of current batch. may not participate in all TIDs of the batch
    public long lastTid;

    /**
     * A vms may not participate in all possible batches
     * In other words, may have gaps
     * This value informs the batch that precedes the
     * current batch
     */
    public long previousBatch;

    public int numberOfTIDsCurrentBatch;

    // data model
    public final Map<String, VmsDataModel> dataSchema;

    // event data model
    public final Map<String, VmsEventSchema> inputEventSchema;

    public final Map<String, VmsEventSchema> outputEventSchema;

    public VmsNode(String host, int port, String vmsIdentifier,
                   long batch, long lastTid, long previousBatch,
                   Map<String, VmsDataModel> dataSchema,
                   Map<String, VmsEventSchema> inputEventSchema,
                   Map<String, VmsEventSchema> outputEventSchema) {
        super(vmsIdentifier, host, port);
        this.batch = batch;
        this.lastTid = lastTid;
        this.previousBatch = previousBatch;
        this.dataSchema = dataSchema;
        this.inputEventSchema = inputEventSchema;
        this.outputEventSchema = outputEventSchema;
        this.numberOfTIDsCurrentBatch = 0;
    }

    public long getLastTid(){
        return this.lastTid;
    }

    @Override
    public String toString() {
        return "{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", identifier='" + identifier + '\'' +
                ", batch=" + batch +
                ", lastTidOfBatch=" + lastTid +
                ", previousBatch=" + previousBatch +
                ", numberOfTIDsCurrentBatch=" + numberOfTIDsCurrentBatch +
                ", inputEvents= "+inputEventSchema.keySet() +
                ", outputEvents= "+outputEventSchema.keySet() +
                '}';
    }

    public IdentifiableNode asIdentifiableNode(){
        return new IdentifiableNode(this.identifier, this.host, this.port);
    }

}
