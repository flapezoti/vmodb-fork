package dk.ku.di.dms.vms.modb.query.execution.operators.count;

import dk.ku.di.dms.vms.modb.common.memory.MemoryRefNode;
import dk.ku.di.dms.vms.modb.definition.key.IKey;
import dk.ku.di.dms.vms.modb.index.interfaces.ReadOnlyIndex;
import dk.ku.di.dms.vms.modb.query.execution.filter.FilterContext;

import java.util.Iterator;

/**
 * Not projecting any other column for now.
 * Count is always integer. But can be indexed or not, like the scan
 * If DISTINCT, must maintain state.
 * 
 */
public final class IndexCount extends AbstractCount {

    public IndexCount(ReadOnlyIndex<IKey> index) {
        super(index, Integer.BYTES);
    }

    public MemoryRefNode run(FilterContext filterContext, IKey... keys){
        int count = 0;
        Iterator<IKey> iterator = this.index.iterator(keys);
        while(iterator.hasNext()){
            IKey key =  iterator.next();
            if(this.index.checkCondition(key, filterContext)){
                count++;
            }
        }
        this.append(count);
        return this.memoryRefNode;
    }

}
