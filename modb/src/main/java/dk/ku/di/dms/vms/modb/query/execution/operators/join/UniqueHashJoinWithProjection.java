package dk.ku.di.dms.vms.modb.query.execution.operators.join;

import dk.ku.di.dms.vms.modb.common.memory.MemoryRefNode;
import dk.ku.di.dms.vms.modb.common.type.DataTypeUtils;
import dk.ku.di.dms.vms.modb.definition.key.IKey;
import dk.ku.di.dms.vms.modb.index.interfaces.ReadOnlyIndex;
import dk.ku.di.dms.vms.modb.query.execution.filter.FilterContext;
import dk.ku.di.dms.vms.modb.query.execution.operators.AbstractMemoryBasedOperator;

import java.util.Iterator;

/**
 * Basic form of hash join
 * where both indexes are hashed by the same
 * set of columns
 */
public class UniqueHashJoinWithProjection extends AbstractMemoryBasedOperator {

    public final ReadOnlyIndex<IKey> leftIndex;
    public final ReadOnlyIndex<IKey> rightIndex;

    // index of the columns
    protected final int[] leftProjectionColumns;
    protected final int[] leftProjectionColumnsSize;

    // this is unnecessary, since it can be from the index directly
    protected final int[] rightProjectionColumns;
    protected final int[] rightProjectionColumnsSize;

    // left = 0, right = 1
    private final boolean[] projectionOrder;

    public UniqueHashJoinWithProjection(
            ReadOnlyIndex<IKey> leftIndex,
            ReadOnlyIndex<IKey> rightIndex,
            int[] leftProjectionColumns,
            int[] leftProjectionColumnsSize,
            int[] rightProjectionColumns,
            int[] rightProjectionColumnsSize,
            boolean[] projectionOrder,
            int entrySize) {
        super(entrySize);
        this.leftIndex = leftIndex;
        this.rightIndex = rightIndex;
        this.leftProjectionColumns = leftProjectionColumns;
        this.leftProjectionColumnsSize = leftProjectionColumnsSize;
        this.rightProjectionColumns = rightProjectionColumns;
        this.rightProjectionColumnsSize = rightProjectionColumnsSize;
        this.projectionOrder = projectionOrder;
    }

    public boolean isHashJoin() { return true; }

    public UniqueHashJoinWithProjection asHashJoin() { return this; }

    public MemoryRefNode run(FilterContext leftFilter, FilterContext rightFilter, IKey... keys) {

        Iterator<IKey> iterator = this.leftIndex.iterator(keys);

        while(iterator.hasNext()){
            IKey nextLeft = iterator.next();
            IKey rightKey = iterator.next();
            if(this.leftIndex.checkCondition(nextLeft, leftFilter) &&
                // do the probing
                this.rightIndex.checkCondition(rightKey, rightFilter)) {
                    append( nextLeft, rightKey,
                            this.leftProjectionColumns, this.leftProjectionColumnsSize,
                            this.rightProjectionColumns, this.rightProjectionColumnsSize
                    );

                }
            iterator.next();
        }

        return this.memoryRefNode;

    }

    public MemoryRefNode run(FilterContext leftFilter, FilterContext rightFilter) {

        Iterator<IKey> outerIterator = this.leftIndex.iterator();

        while(outerIterator.hasNext()){
            IKey leftKey = outerIterator.next();
            if(this.leftIndex.checkCondition(leftKey, leftFilter)){
                IKey rightKey = outerIterator.next();
                if(this.rightIndex.checkCondition(rightKey, rightFilter)) {
                    append( leftKey, rightKey, this.leftProjectionColumns, this.leftProjectionColumnsSize,
                            this.rightProjectionColumns, this.rightProjectionColumnsSize );

                }
            }
            outerIterator.next();
        }

        return this.memoryRefNode;
    }

    /**
     * Copy the column values from both relations in the order of projection
     * This is to avoid rechecking procedures by the caller
     * Easier to ensure (implicitly) that remote calls between modules remain consistent
     * just by following conventions
     */
    private void append(IKey keyLeft, IKey keyRight,
                        int[] leftProjectionColumns, int[] leftValueSizeInBytes,
                        int[] rightProjectionColumns, int[] rightValueSizeInBytes){

        int leftProjIdx = 0;
        int rightProjIdx = 0;

        Object[] leftRecord = this.leftIndex.record(keyLeft);
        // not the address of left iterator
        Object[] rightRecord = this.rightIndex.record(keyRight);

        for(int projOrdIdx = 0; projOrdIdx < projectionOrder.length; projOrdIdx++) {
            // left
            if(!projectionOrder[projOrdIdx]){
                DataTypeUtils.callWriteFunction( this.currentBuffer.address(), this.leftIndex.schema().columnDataType( leftProjIdx ), leftRecord[leftProjectionColumns[leftProjIdx]] );
                this.currentBuffer.forwardOffset(leftValueSizeInBytes[leftProjIdx]);
                leftProjIdx++;
            } else {
                DataTypeUtils.callWriteFunction( this.currentBuffer.address(), this.rightIndex.schema().columnDataType( rightProjIdx ), rightRecord[rightProjectionColumns[rightProjIdx]] );
                this.currentBuffer.forwardOffset(rightValueSizeInBytes[rightProjIdx]);
                rightProjIdx++;
            }
        }

    }

}
