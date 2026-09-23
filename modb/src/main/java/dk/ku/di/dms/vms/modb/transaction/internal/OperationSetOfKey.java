package dk.ku.di.dms.vms.modb.transaction.internal;

import dk.ku.di.dms.vms.modb.transaction.multiversion.TransactionWrite;
import dk.ku.di.dms.vms.modb.transaction.multiversion.WriteType;

/**
 * The set of operations applied to a given index key
 * Maybe the references to DeleteOp and InsertOp are not necessary.
 * Since we have the last write type and the cached entity,
 * they naturally reference the insert (or last updated)...
 */
public final class OperationSetOfKey extends OneWriterMultiReadersLIFO<Long, TransactionWrite> {

    /**
     * Nothing impedes the user from deleting and inserting again the same record.
     * A RW/W thread are sequentially spawned, this value always returns correct results.
     * Serves a cache for the last stable write for this key. To avoid traversing all writes performed by TIDs in the history map.
     * It is written during RW/W tasks, so can only be used by on-flight RW/W tasks
     */
    public volatile WriteType lastWriteType;

    public OperationSetOfKey(WriteType initialWriteType){
        this.lastWriteType = initialWriteType;
    }

}
