package dk.ku.di.dms.vms.modb.query.execution.operators.sum;

import dk.ku.di.dms.vms.modb.common.memory.MemoryRefNode;
import dk.ku.di.dms.vms.modb.common.type.DataType;
import dk.ku.di.dms.vms.modb.definition.key.IKey;
import dk.ku.di.dms.vms.modb.index.AbstractIndex;
import dk.ku.di.dms.vms.modb.index.interfaces.ReadOnlyIndex;
import dk.ku.di.dms.vms.modb.query.execution.filter.FilterContext;
import dk.ku.di.dms.vms.modb.query.execution.operators.AbstractMemoryBasedOperator;

import java.util.Iterator;

public class Sum extends AbstractMemoryBasedOperator {

    protected final ReadOnlyIndex<IKey> index;

    protected final int columnIndex;

    protected final DataType dataType;

    public Sum(DataType dataType,
                    int columnIndex,
                    AbstractIndex<IKey> index) {
        super(dataType.value);
        this.dataType = dataType;
        this.index = index;
        this.columnIndex = columnIndex;
    }

    @SuppressWarnings("unchecked, rawtypes")
    public MemoryRefNode run(
            FilterContext filterContext){
        SumUtils.SumOperation sumOperation = SumUtils.buildSumOperation(this.dataType);
        Iterator<IKey> iterator = this.index.iterator();
        while(iterator.hasNext()){
            IKey key = iterator.next();
            if(this.index.checkCondition(key, filterContext)){
                Object val = this.index.record(key)[this.columnIndex];
                sumOperation.accept(val);
            }
            iterator.next();
        }
        this.appendResult(sumOperation);
        return memoryRefNode;
    }

    protected void appendResult(SumUtils.SumOperation<?> sumOperation){
        switch (dataType){
            case INT -> this.currentBuffer.append( sumOperation.asInt() );
            case FLOAT -> this.currentBuffer.append( sumOperation.asFloat() );
            case DOUBLE -> this.currentBuffer.append( sumOperation.asDouble() );
            case LONG -> this.currentBuffer.append( sumOperation.asLong() );
        }
    }

}
