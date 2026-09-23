package dk.ku.di.dms.vms.sdk.core.scheduler.tracking;

import dk.ku.di.dms.vms.sdk.core.operational.VmsComplexTransactionTask;
import dk.ku.di.dms.vms.sdk.core.operational.VmsTransactionTaskResult;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Future;

/**
 * The context of the tasks from a transaction (thus having the same TID) in a single VMS.
 * The tasks found in the lists are READY to be scheduled for execution.
 * In other words, all inputs are fulfilled.
 */
public class ComplexVmsTransactionTrackingContext implements IVmsTransactionTrackingContext {

    private int nextTaskIdentifier;

    // the R tasks ready for execution
    public final List<VmsComplexTransactionTask> readTasks;

    // the RW and W ready for execution
    public final Queue<VmsComplexTransactionTask> writeTasks;

    public final List<Future<VmsTransactionTaskResult>> submittedTasks;

    public final List<VmsTransactionTaskResult> resultTasks;

    public ComplexVmsTransactionTrackingContext(int numReadTasks, int numReadWriteTasks, int numWriteTasks) {
        this.nextTaskIdentifier = 1;
        this.readTasks = new ArrayList<>(numReadTasks);
        this.writeTasks = new ArrayDeque<>(numReadWriteTasks + numWriteTasks);
        int total = numReadTasks + numWriteTasks + numWriteTasks;
        this.submittedTasks = new ArrayList<>(total);
        this.resultTasks = new ArrayList<>(total);
    }

    public int readAndIncrementNextTaskIdentifier(){
        return this.nextTaskIdentifier++;
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public ComplexVmsTransactionTrackingContext asComplex(){
        return this;
    }

}
