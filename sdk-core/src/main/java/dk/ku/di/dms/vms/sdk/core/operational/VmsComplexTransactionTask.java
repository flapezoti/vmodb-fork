package dk.ku.di.dms.vms.sdk.core.operational;

import dk.ku.di.dms.vms.modb.api.enums.TransactionTypeEnum;
import dk.ku.di.dms.vms.modb.common.transaction.ITransactionManager;

import java.util.concurrent.Callable;

/**
 * A class that tracks the possible many events that form the input of a data operation.
 * In other words, once all the input events arrive, the transaction is ready for execution.
 */
public final class VmsComplexTransactionTask implements Callable<VmsTransactionTaskResult> {

    // this is the global tid
    private final long tid;

    private final long lastTid;

    private final long batch;

    private final ITransactionManager transactionalHandler;

    // the information necessary to run the method
    private final VmsTransactionSignature signature;

    // internal identification of this specific task in the scheduler
    // used to allow atomic visibility
    // (can be later used to specify ordering criteria between tasks)
    private int identifier;

    private final Object[] inputs;

    private int remainingInputs;

    public VmsComplexTransactionTask(ITransactionManager transactionalHandler, long tid, long lastTid, long batch, VmsTransactionSignature signature, int inputSize){
        this.transactionalHandler = transactionalHandler;
        this.tid = tid;
        this.lastTid = lastTid;
        this.batch = batch;
        this.signature = signature;
        this.inputs = new Object[inputSize];
        this.remainingInputs = inputSize;
    }

    public void putEventInput(int index, Object event){
        this.inputs[index] = event;
        this.remainingInputs--;
    }

    /**
     * Only set for write transactions.
     * @param identifier a task identifier among the tasks of a TID
     */
    public void setIdentifier(int identifier){
        this.identifier = identifier;
    }

    public TransactionTypeEnum transactionType(){
        return this.signature.transactionType();
    }

    public long tid() {
        return this.tid;
    }

    public boolean isReady(){
        return this.remainingInputs == 0;
    }

    public VmsTransactionSignature signature(){
        return this.signature;
    }

    @Override
    public VmsTransactionTaskResult call() {
        // register thread in the transaction facade
        try{
            var txCtx = this.transactionalHandler.beginTransaction(this.tid, this.identifier, this.lastTid, this.signature.transactionType() == TransactionTypeEnum.R);
            Object output = this.signature.method().invoke(this.signature.vmsInstance(), this.inputs);
            // can be null, given we have terminal events (void method)
            // could also be terminal and generate event... maybe an external system wants to consume
            // then send to the leader...
            OutboundEventResult eventOutput = new OutboundEventResult(this.tid, this.batch, this.signature.outputQueue(), output);
            if(this.signature.transactionType() != TransactionTypeEnum.R){
                this.transactionalHandler.commit();
            }
            return new VmsTransactionTaskResult(
                    this.tid,
                    this.identifier,
                    eventOutput,
                    VmsTransactionTaskResult.Status.SUCCESS);
        } catch (Exception e) {
            // (i) whether to return to the scheduler or (ii) to push to the payload handler for forwarding it to the queue
            // we can only notify it because the scheduler does not need to know the events. the scheduler just needs to
            // know whether the processing of events has been completed can be directly sent to the microservice outside
            // taskResultQueue.add(new VmsTransactionTaskResult(threadId, tid, identifier, true));
            return new VmsTransactionTaskResult(
                    this.tid,
                    this.identifier,
                    null,
                    VmsTransactionTaskResult.Status.FAILURE);
        }

    }

    public Object input(int index) {
        return inputs[index];
    }

}