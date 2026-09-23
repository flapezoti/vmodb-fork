package dk.ku.di.dms.vms.modb.query.execution.operators.join;

import dk.ku.di.dms.vms.modb.common.memory.MemoryRefNode;
import dk.ku.di.dms.vms.modb.common.type.DataTypeUtils;
import dk.ku.di.dms.vms.modb.definition.key.IKey;
import dk.ku.di.dms.vms.modb.index.interfaces.ReadOnlyIndex;
import dk.ku.di.dms.vms.modb.query.execution.filter.FilterContext;
import dk.ku.di.dms.vms.modb.query.execution.operators.AbstractMemoryBasedOperator;

import java.util.Iterator;

/**
 * Hash join
 * where outer index is unique hash
 * and inner index is non unique hash
 */
public class UniqueHashJoinNonUniqueHashWithProjection extends AbstractMemoryBasedOperator {

    public final ReadOnlyIndex<IKey> leftIndex;
    public final ReadOnlyIndex<IKey> rightIndex;

    // index of the columns
    protected final int[] leftProjectionColumns;
    protected final int[] leftProjectionColumnsSize;

    // this is unnecessary, since it can be get from the index directly
    protected final int[] rightProjectionColumns;
    protected final int[] rightProjectionColumnsSize;

    // left = 0, right = 1
    private final boolean[] projectionOrder;

    public UniqueHashJoinNonUniqueHashWithProjection(
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

    public MemoryRefNode run(FilterContext leftFilter, FilterContext rightFilter, IKey... keys) {

        Iterator<IKey> leftIterator = this.leftIndex.iterator(keys);
        Iterator<IKey> rightIterator;

        while(leftIterator.hasNext()) {

            IKey leftKey = leftIterator.next();
            if (!this.leftIndex.checkCondition(leftKey, leftFilter)) continue;

            rightIterator = this.rightIndex.iterator(leftIterator.next());
            if (!rightIterator.hasNext()) {
                leftIterator.next();
                continue;
            }
            IKey rightKey = leftIterator.next();
            while (rightIterator.hasNext()) {
                if (this.rightIndex.checkCondition(rightKey, rightFilter)) {
                    append(leftKey, rightKey,
                            leftProjectionColumns, leftProjectionColumnsSize,
                            rightProjectionColumns, rightProjectionColumnsSize
                    );
                }
                rightIterator.next();
            }

            leftIterator.next();
        }

        return memoryRefNode;

    }

    /**
     * Copy the column values from both relations in the order of projection
     * This is to avoid rechecking procedures by the caller
     * Easier to ensure (implicitly) that remote calls between modules remain consistent
     * just by following conventions
     */
    private void append(IKey leftKey, IKey rightKey,
                        int[] leftProjectionColumns, int[] leftValueSizeInBytes,
                        int[] rightProjectionColumns, int[] rightValueSizeInBytes){

        int leftProjIdx = 0;
        int rightProjIdx = 0;

        Object[] leftRecord = this.leftIndex.record(leftKey);
        Object[] rightRecord = this.rightIndex.record(rightKey);

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
