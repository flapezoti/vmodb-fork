package dk.ku.di.dms.vms.sdk.core.scheduler.complex;

import dk.ku.di.dms.vms.sdk.core.operational.OutboundEventResult;
import dk.ku.di.dms.vms.sdk.core.scheduler.IVmsTransactionResult;

import java.util.List;

/**
 * A collection of results of a given transaction (tid) in a specific VMS
 * It bundles everything necessary to commit this transaction as part of a batch
 */
public final class VmsComplexTransactionResult implements IVmsTransactionResult {

    private final long tid;

    private final List<OutboundEventResult> resultTasks;

    public VmsComplexTransactionResult(long tid, List<OutboundEventResult> resultTasks) {
        this.tid = tid;
        this.resultTasks = resultTasks;
    }

    @Override
    public long tid() {
        return this.tid;
    }

    @Override
    public OutboundEventResult getOutboundEventResult() {
        return null;
    }

    @Override
    public List<OutboundEventResult> getOutboundEventResults() {
        return this.resultTasks;
    }
}
