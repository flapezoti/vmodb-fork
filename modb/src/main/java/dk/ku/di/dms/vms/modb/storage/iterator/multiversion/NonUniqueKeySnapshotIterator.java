//package dk.ku.di.dms.vms.modb.storage.iterator.multiversion;
//
//import dk.ku.di.dms.vms.modb.definition.key.IKey;
//import dk.ku.di.dms.vms.modb.index.interfaces.ReadOnlyBufferIndex;
//import dk.ku.di.dms.vms.modb.storage.iterator.IRecordIterator;
//import dk.ku.di.dms.vms.modb.transaction.multiversion.index.NonUniqueSecondaryIndex;
//
///**
// * Only allowed values are exposed as part of the iteration.
// * It has an underlying non-unique hash index
// */
//public final class NonUniqueKeySnapshotIterator implements IRecordIterator<IKey> {
//
//    /**
//     * Can only be a {@link NonUniqueSecondaryIndex}
//     */
//    private final ReadOnlyBufferIndex<IKey> index;
//    private final IRecordIterator<IKey> recordIterator;
//
//    public NonUniqueKeySnapshotIterator(ReadOnlyBufferIndex<IKey> index, IRecordIterator recordIterator) {
//        this.index = index;
//        this.recordIterator = recordIterator;
//    }
//
//    @Override
//    public boolean hasElement() {
//        return recordIterator.hasElement();
//    }
//
//    @Override
//    public void next() {
//
//        // TODO finish
//        // additional checks, i.e., the visibility
//
//        // strategy. iterate over index keys first
//        // cache items that have been iterated
//
//        // at the end, iterate over main memory, bypassing the keys that has already been checked
//
//        // is it visible?
//        while( ! this.index.exists( get() ) ){
//
//            // FIXME this doesn't ot iterate over new keys, the underlying iterator cannot seen them
//            recordIterator.next();
//        }
//
//        return;
//
//    }
//
//
//    @Override
//    public IKey get() {
//        return null;
//    }
//
//    @Override
//    public long address() {
//        return recordIterator.address();
//    }
//
//}
