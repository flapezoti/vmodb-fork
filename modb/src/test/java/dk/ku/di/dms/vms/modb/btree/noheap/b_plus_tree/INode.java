package dk.ku.di.dms.vms.modb.btree.noheap.b_plus_tree;

import dk.ku.di.dms.vms.modb.definition.key.IKey;
import dk.ku.di.dms.vms.modb.storage.iterator.IRecordIterator;

public interface INode {

    // necessary for splitting the node values
    // last key should always refer to the last key of the left
    // side of the divided bucket of values
    int lastKey();

    // same API for leaf and non-leaf nodes
    INode insert(IKey key, long srcAddress);

    // behavior differs across internal and leaf nodes
    IRecordIterator<Long> iterator();

}
