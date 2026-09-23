package dk.ku.di.dms.vms.modb.transaction;

import dk.ku.di.dms.vms.modb.common.transaction.ITransactionContext;
import dk.ku.di.dms.vms.modb.transaction.multiversion.index.IMultiVersionIndex;

import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Closeable object to facilitate object handling
 */
public final class TransactionContext implements ITransactionContext {

    private static final Deque<Set<IMultiVersionIndex>> INDEX_SET_BUFFER = new ConcurrentLinkedDeque<>();

    public final long tid;

    public final long lastTid;

    public final boolean readOnly;

    public final Set<IMultiVersionIndex> indexes;

    public TransactionContext(long tid, long lastTid, boolean readOnly) {
        this.tid = tid;
        this.lastTid = lastTid;
        this.readOnly = readOnly;

        Set<IMultiVersionIndex> chosenIndexSet;
        if(!readOnly) {
             chosenIndexSet = INDEX_SET_BUFFER.poll();
            // 4 maximum indexes used per task usually
             if(chosenIndexSet == null)
            chosenIndexSet = new HashSet<>(4);
        } else {
            chosenIndexSet = Collections.emptySet();
        }
        this.indexes = chosenIndexSet;
    }

    /**
     * To avoid another thread acquiring and modifying the indexes set concurrently,
     * call close explicitly outside a try with block
      */
    @Override
    public void release(){
        if(this.readOnly) return;
        this.indexes.clear();
        INDEX_SET_BUFFER.addLast(this.indexes);
    }

}
