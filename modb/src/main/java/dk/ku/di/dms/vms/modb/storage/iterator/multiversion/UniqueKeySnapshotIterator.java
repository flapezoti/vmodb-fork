//package dk.ku.di.dms.vms.modb.storage.iterator.multiversion;
//
//import dk.ku.di.dms.vms.modb.definition.key.IKey;
//import dk.ku.di.dms.vms.modb.index.interfaces.ReadOnlyBufferIndex;
//import dk.ku.di.dms.vms.modb.storage.iterator.IRecordIterator;
//
//import java.util.List;
//
///**
// * TODO finish Just an iterator operators can iterate over safely without exposing inconsistent data
// */
//public class UniqueKeySnapshotIterator implements IRecordIterator<IKey> {
//
//    // just call exists for each entry in the hast table. this possibly takes a long time
//    // unless we have linked list of existing records
//    // for no keys, maybe it is better to iterate over some non-unique index for this table
//    // perhaps this is the best approach, would allow avoiding checking empty blocks
//
//    private ReadOnlyBufferIndex<IKey> index;
//    private IKey[] keys;
//
//    public UniqueKeySnapshotIterator(ReadOnlyBufferIndex<IKey> index, IKey[] keys){
//        this.index = index;
//        this.keys = keys;
//    }
//
//    @Override
//    public boolean hasElement() {
//
//        List.of(1,2,3).spliterator();
//
//        return false;
//    }
//
//    @Override
//    public IKey get() {
//        return null;
//    }
//
//    @Override
//    public void next() {
//
//    }
//
//}
