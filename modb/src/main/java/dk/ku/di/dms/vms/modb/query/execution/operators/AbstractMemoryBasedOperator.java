package dk.ku.di.dms.vms.modb.query.execution.operators;

import dk.ku.di.dms.vms.modb.common.memory.MemoryManager;
import dk.ku.di.dms.vms.modb.common.memory.MemoryRefNode;
import dk.ku.di.dms.vms.modb.storage.record.AppendOnlyBufferOld;

public abstract class AbstractMemoryBasedOperator extends AbstractSimpleOperator {

    // the first node (but last to be acquired) of the memory segment nodes
    protected MemoryRefNode memoryRefNode = null;

    protected AppendOnlyBufferOld currentBuffer;

    public AbstractMemoryBasedOperator(int entrySize) {
        super(entrySize);
    }

    /**
     * Just abstracts on which memory segment a result will be written to
     * Default method. Operators can create their own
     */
    protected void ensureMemoryCapacity(){
        if(this.currentBuffer != null && (this.currentBuffer.size() - (this.currentBuffer.address() - this.currentBuffer.nextOffset())) > this.entrySize){
            return;
        }
        // else, get a new memory segment
        MemoryRefNode claimed = MemoryManager.getTemporaryDirectMemory();
        claimed.next = this.memoryRefNode;
        this.memoryRefNode = claimed;
        this.currentBuffer = new AppendOnlyBufferOld(claimed.address(), claimed.bytes());
    }

    /**
     * For operators that don't know the amount of records from start
     * @param size the size needed next to allocate a new tuple
     */
    protected void ensureMemoryCapacity(int size){
        if(this.currentBuffer != null && this.currentBuffer.size() - this.currentBuffer.address() > size) {
            return;
        }
        // else, get a new memory segment
        MemoryRefNode claimed = MemoryManager.getTemporaryDirectMemory(size);
        claimed.next = this.memoryRefNode;
        this.memoryRefNode = claimed;
        this.currentBuffer = new AppendOnlyBufferOld(claimed.address(), claimed.bytes());
    }

}
