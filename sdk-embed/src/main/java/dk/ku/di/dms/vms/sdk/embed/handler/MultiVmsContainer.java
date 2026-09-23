package dk.ku.di.dms.vms.sdk.embed.handler;

import dk.ku.di.dms.vms.modb.common.schema.network.node.IdentifiableNode;
import dk.ku.di.dms.vms.modb.common.schema.network.transaction.TransactionEvent;

/**
 * A container of consumer VMS workers to facilitate
 * scalable pushing of transaction events
 */
public final class MultiVmsContainer implements IVmsContainer {

    private final IdentifiableNode node;
    private final ConsumerVmsWorker[] consumerVmsWorkers;
    private int next;

    // init a container with the initial consumer VMS
    MultiVmsContainer(ConsumerVmsWorker initialConsumerVms, IdentifiableNode node, int numVmsWorkers) {
        this.consumerVmsWorkers = new ConsumerVmsWorker[numVmsWorkers];
        this.consumerVmsWorkers[0] = initialConsumerVms;
        this.next = 0;
        this.node = node;
    }

    public synchronized void addConsumerVms(ConsumerVmsWorker vmsWorker) {
        this.next++;
        this.consumerVmsWorkers[this.next] = vmsWorker;
        if(this.next == this.consumerVmsWorkers.length-1) this.next = 0;
    }

    @Override
    public void queue(TransactionEvent.PayloadRaw payload){
        this.consumerVmsWorkers[this.next].queue(payload);
        if(this.next == this.consumerVmsWorkers.length-1){
            this.next = 0;
        } else {
            this.next += 1;
        }
    }

    @Override
    public String identifier(){
        return this.node.identifier;
    }

    @Override
    public void stop(){
        for(var consumer : consumerVmsWorkers){
            consumer.stop();
        }
    }
}


