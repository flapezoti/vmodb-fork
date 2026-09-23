package dk.ku.di.dms.vms.sdk.core.scheduler.tracking;

import dk.ku.di.dms.vms.sdk.core.operational.VmsComplexTransactionTask;
import dk.ku.di.dms.vms.sdk.core.operational.VmsTransactionTaskResult;

import java.util.concurrent.Future;

/**
 * For cases where only one task is involved
 * The idea is to avoid the overhead of creating many data structures
 * for every new task as found in the complex ({@link ComplexVmsTransactionTrackingContext}
 */
public class SimpleVmsTransactionTrackingContext implements IVmsTransactionTrackingContext {

    public VmsComplexTransactionTask task;

    public Future<VmsTransactionTaskResult> future;

    public VmsTransactionTaskResult result;

    @Override
    public SimpleVmsTransactionTrackingContext asSimple(){
        return this;
    }

}
