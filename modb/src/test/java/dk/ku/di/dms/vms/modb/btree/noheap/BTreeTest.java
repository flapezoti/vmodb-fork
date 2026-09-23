package dk.ku.di.dms.vms.modb.btree.noheap;

import dk.ku.di.dms.vms.modb.btree.noheap.b_plus_tree.INode;
import dk.ku.di.dms.vms.modb.btree.noheap.b_plus_tree.NonLeafNode;
import dk.ku.di.dms.vms.modb.common.memory.MemoryUtils;
import dk.ku.di.dms.vms.modb.definition.key.IntKey;
import dk.ku.di.dms.vms.modb.storage.iterator.IRecordIterator;
import org.junit.Test;

public class BTreeTest {

    private static final int PAGE_SIZE_KB = 128;

    // several insertions and then walk through the entries
    @Test
    public void testExecutor(){

        NonLeafNode parent = NonLeafNode.parent( PAGE_SIZE_KB );

        parent.insert(IntKey.of(1), 1L);
        parent.insert(IntKey.of(2), 2L);
        parent.insert(IntKey.of(3), 3L);
        parent.insert(IntKey.of(4), 4L);
        parent.insert(IntKey.of(5), 5L);
        parent.insert(IntKey.of(6), 6L);
        parent.insert(IntKey.of(7), 7L);
        parent.insert(IntKey.of(8), 8L);
        parent.insert(IntKey.of(9), 9L);
        parent.insert(IntKey.of(10), 10L);
        parent.insert(IntKey.of(11), 11L);

        INode newNonLeaf =  parent.insert(IntKey.of(12), 12L);

        System.out.println("Old parent last key: "+ parent.lastKey());
        System.out.println("New parent last key: "+ newNonLeaf.lastKey());

        IRecordIterator<Long> iterator = parent.iterator();

        while(iterator.hasNext()){
            System.out.println(MemoryUtils.UNSAFE.getInt( iterator.next() + 1 + Long.BYTES + Integer.BYTES ));
            iterator.next();
        }

    }

}
