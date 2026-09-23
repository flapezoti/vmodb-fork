package dk.ku.di.dms.vms.modb.storage.iterator.non_unique;

import dk.ku.di.dms.vms.modb.storage.record.OrderedRecordBuffer;

import java.util.Iterator;

/**
 * Encapsulates the iteration over buckets of a non-unique hash index
 */
public final class BucketIterator implements Iterator<RecordBucketIterator> {

    private final int size;

    private final OrderedRecordBuffer[] buffers;

    // how many buckets have been iterated so far
    private int progress;

    public BucketIterator(OrderedRecordBuffer[] buffers){
        this.buffers = buffers;
        this.size = buffers.length;
        this.progress = 0;
    }

    public BucketIterator(OrderedRecordBuffer buffer){
        this.buffers = new OrderedRecordBuffer[]{ buffer };
        this.size = 1;
        this.progress = 0;
    }

    @Override
    public boolean hasNext() {

        while(progress < size && buffers[progress].size() == 0){
            this.progress++;
        }

        return progress < size;
    }

    /**
     * This method should always come after a hasNext call
     * @return the record address
     */
    @Override
    public RecordBucketIterator next() {
        this.progress++;
        return new RecordBucketIterator(this.buffers[progress-1]);
    }

}
