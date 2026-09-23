package dk.ku.di.dms.vms.sdk.core.metadata;

import dk.ku.di.dms.vms.modb.common.data_structure.IdentifiableNode;
import dk.ku.di.dms.vms.sdk.core.operational.VmsTransactionSignature;

import java.util.ArrayList;
import java.util.List;

/**
 * It contains the method signatures for a given transaction
 * as well as aggregated contextual information (to create a flock)
 */
public final class VmsTransactionMetadata {

    public int numReadTasks;
    public int numWriteTasks;
    public int numReadWriteTasks;

    public int numTasksWithMoreThanOneInput;

    public List<IdentifiableNode<VmsTransactionSignature>> signatures;

    public VmsTransactionMetadata(){
        this.numReadTasks = 0;
        this.numWriteTasks = 0;
        this.numReadWriteTasks = 0;
        this.numTasksWithMoreThanOneInput = 0;
        this.signatures = new ArrayList<>();
    }

}
