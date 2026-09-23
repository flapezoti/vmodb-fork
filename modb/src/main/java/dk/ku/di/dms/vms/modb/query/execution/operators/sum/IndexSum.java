package dk.ku.di.dms.vms.modb.query.execution.operators.sum;

import dk.ku.di.dms.vms.modb.common.memory.MemoryRefNode;
import dk.ku.di.dms.vms.modb.common.type.DataType;
import dk.ku.di.dms.vms.modb.definition.key.IKey;
import dk.ku.di.dms.vms.modb.index.interfaces.ReadWriteIndex;
import dk.ku.di.dms.vms.modb.query.execution.filter.FilterContext;

import java.util.Iterator;

/**
 *
 * On the other side, the sum is type-dependent. Can be done while the records are scanned.
 */
public final class IndexSum extends Sum {

    public IndexSum(DataType dataType,
                    int columnIndex,
                    ReadWriteIndex<IKey> index) {
        super(dataType, columnIndex, index);
    }

    @SuppressWarnings("unchecked, rawtypes")
    public MemoryRefNode run(FilterContext filterContext, IKey... keys){
        SumUtils.SumOperation sumOperation = SumUtils.buildSumOperation(this.dataType);
        Iterator<IKey> iterator = this.index.iterator(keys);
        while(iterator.hasNext()){
            IKey key = iterator.next();
            if(this.index.checkCondition(key, filterContext)){
                sumOperation.accept(this.index.record(key)[this.columnIndex]);
            }

        }
        this.appendResult(sumOperation);
        return this.memoryRefNode;
    }

}
