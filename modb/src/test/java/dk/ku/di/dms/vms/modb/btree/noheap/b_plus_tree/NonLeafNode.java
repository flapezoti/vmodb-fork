package dk.ku.di.dms.vms.modb.btree.noheap.b_plus_tree;

import dk.ku.di.dms.vms.modb.common.memory.MemoryManager;
import dk.ku.di.dms.vms.modb.common.memory.MemoryRefNode;
import dk.ku.di.dms.vms.modb.common.memory.MemoryUtils;
import dk.ku.di.dms.vms.modb.definition.key.IKey;
import dk.ku.di.dms.vms.modb.storage.iterator.IRecordIterator;
import dk.ku.di.dms.vms.modb.storage.record.AppendOnlyBufferOld;
import dk.ku.di.dms.vms.modb.storage.record.OrderedRecordBuffer;

/**
 * how to represent the leaf nodes? with {@link OrderedRecordBuffer}
 * how to represent the internal (and parent) nodes? a proper data structure:
 * every key node has address left, right
 * sequentially put key by key
 * { key value, left node, right node }
 * { INT, LONG, LONG }
 * To allow for fast search in the keys....
 * But if the integer are sequentially placed, it is just a matter of doing log n search
 * TODO skip list to navigate faster in this node
 */
public class NonLeafNode implements INode {

    // to be used when writing to disk
//    public static final byte identifier = 0;

    public static final int nonLeafEntrySize = Integer.BYTES; // + (2 * Long.BYTES);

    public final int pageSize;
    public final int branchingFactor;

    public final AppendOnlyBufferOld buffer;

//    private long first;
//    private long last;
//    private long half;

    public int nKeys;

    public final INode[] children;

    // public static final int deltaPrevious = Integer.BYTES;
    public static final int deltaNext = Integer.BYTES; // + Long.BYTES;

    public final boolean parent;

    private NonLeafNode(int pageSize){
        this.pageSize = pageSize;
        // this.branchingFactor = (pageSize / nonLeafEntrySize) - 1;
        this.branchingFactor = 5;
        this.parent = true;
        MemoryRefNode memoryRefNode = MemoryManager.getTemporaryDirectMemory( pageSize );
        this.buffer = new AppendOnlyBufferOld( memoryRefNode.address, memoryRefNode.bytes );
        this.nKeys = 0;
        this.children = new INode[this.branchingFactor + 1]; // + 1 because of leaf nodes
        this.children[0] = LeafNode.leaf( this.pageSize );
    }

    public static NonLeafNode parent(int pageSize){
//        NonLeafNode parent =
        return new NonLeafNode(pageSize);
        // parent.leafSize = leafSize; // spread over internal nodes
        // parent.branchingFactor = branchingFactor;
        // allocate another append only buffer and then an ordered record buffer
        // parent.children[0] = OrderedRecordBuffer
        // return parent;
    }

    private NonLeafNode(int pageSize, int nKeys, INode[] children, AppendOnlyBufferOld buffer){
        this.pageSize = pageSize;
        this.branchingFactor = (pageSize / nonLeafEntrySize) - 1;
        this.buffer = buffer;
        this.parent = false;
        this.nKeys = nKeys;
        this.children = children;
    }

    public static NonLeafNode internal(int pageSize, int nKeys, INode[] children, AppendOnlyBufferOld buffer){
        return new NonLeafNode(pageSize, nKeys, children, buffer);
    }

    @Override
    public int lastKey() {
        return MemoryUtils.UNSAFE.getInt( this.buffer.address() + ((long) nonLeafEntrySize * (this.nKeys - 1)) );
    }

    @Override
    public INode insert(IKey key, long srcAddress) {
        // find the node
        long currAddr = this.buffer.address();
        int i;
        for (i = 0; i < this.nKeys; i++) {
            int currKey = MemoryUtils.UNSAFE.getInt(currAddr);
            if (currKey > key.hashCode()) break;
            currAddr = currAddr + deltaNext;
        }
        INode newNode = children[i].insert(key, srcAddress);
        if (newNode != null) {
            this.children[i + 1] = newNode;
            buffer.append( children[i].lastKey() );
            nKeys++;
            if(this.nKeys == this.branchingFactor) {
                // overflow
                return overflow();
            }
        }
        return null;
    }

    private INode overflow() {

        int half = (int) Math.ceil((double)this.branchingFactor / 2);

        // truncate left keys
        this.nKeys = half;

        MemoryRefNode memoryRefNode = MemoryManager.getTemporaryDirectMemory( this.pageSize );
        AppendOnlyBufferOld rightBuffer = new AppendOnlyBufferOld( memoryRefNode.address, memoryRefNode.bytes );

        // copy keys to new buffer (from truncated offset + 1 to branching factor)
        MemoryUtils.UNSAFE.copyMemory( this.buffer.address() + ( (long) (this.branchingFactor - nKeys + 1) * nonLeafEntrySize),
                rightBuffer.address(), (long) (this.branchingFactor - half) * nonLeafEntrySize );

        INode[] childrenRight = new INode[this.branchingFactor + 1];

        int ci = 0;
        for(int i = half; i < this.branchingFactor; i++){
            childrenRight[ci] = this.children[i];
            ci++;
            this.children[i] = null;
        }

        return NonLeafNode.internal( this.pageSize, ci, childrenRight, rightBuffer );

    }

    /**
     * Encapsulates many iterators.
     * The iterators of the leaf nodes.
     * @return an iterator of the respective leaf nodes
     */
    public IRecordIterator<Long> iterator(){
        return this.children[1].iterator();
//        for(INode node : this.children){
//            return node.iterator();
//        }
//        return null;
    }

}