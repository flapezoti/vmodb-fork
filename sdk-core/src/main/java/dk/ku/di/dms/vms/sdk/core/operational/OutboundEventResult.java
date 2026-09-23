package dk.ku.di.dms.vms.sdk.core.operational;

import dk.ku.di.dms.vms.sdk.core.scheduler.IVmsTransactionResult;

/**
 * Just a placeholder.
 * The object needs to be converted before being sent
 */
public final class OutboundEventResult implements IVmsTransactionResult {

    private final long tid;
    private final long batch;
    private final String outputQueue;
    private final Object output;

    public OutboundEventResult(long tid, long batch, String outputQueue, Object output) {
        this.tid = tid;
        this.batch = batch;
        this.outputQueue = outputQueue;
        this.output = output;
    }

    @Override
    public long tid() {
        return this.tid;
    }

    @Override
    public OutboundEventResult getOutboundEventResult() {
        return this;
    }

    public String outputQueue() {
        return this.outputQueue;
    }

    public long batch() {
        return this.batch;
    }

    public Object output() {
        return this.output;
    }
}