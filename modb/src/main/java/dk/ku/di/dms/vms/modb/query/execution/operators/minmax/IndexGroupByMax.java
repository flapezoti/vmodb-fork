package dk.ku.di.dms.vms.modb.query.execution.operators.minmax;

import dk.ku.di.dms.vms.modb.common.data_structure.Tuple;
import dk.ku.di.dms.vms.modb.definition.Schema;
import dk.ku.di.dms.vms.modb.definition.key.IKey;
import dk.ku.di.dms.vms.modb.definition.key.KeyUtils;
import dk.ku.di.dms.vms.modb.transaction.TransactionContext;
import dk.ku.di.dms.vms.modb.transaction.multiversion.index.IMultiVersionIndex;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class IndexGroupByMax extends IndexAggregateScan {

    public IndexGroupByMax(int entrySize, IMultiVersionIndex index, int[] projectionColumns, Schema schema, int[] indexColumns, int minMaxColumn, int limit) {
        super(entrySize, index, projectionColumns, schema, indexColumns, minMaxColumn, limit);
    }

    public List<Object[]> runAsEmbedded(TransactionContext txCtx){
        Map<GroupByKey, Tuple<Comparable<?>,Object[]>> maxMap = new HashMap<>();
        Iterator<Object[]> iterator = this.index.iterator(txCtx);
        // build hash with min per group (defined in group by)
        while(iterator.hasNext()){
            this.compute(iterator.next(), maxMap);
        }
        return this.project(maxMap);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void compute(Object[] record, Map<GroupByKey, Tuple<Comparable<?>, Object[]>> minMap) {
        if(record == null) return;
        // hash the group by columns
        IKey pk = KeyUtils.buildRecordKey(this.schema.getPrimaryKeyColumns(), record);
        IKey groupByKey = KeyUtils.buildRecordKey(this.indexColumns, record);
        GroupByKey groupKey = new GroupByKey( groupByKey.hashCode(), pk );
        if(!minMap.containsKey(groupKey)){
            minMap.put(groupKey, new Tuple<>( (Comparable<?>) record[this.minMaxColumn], record) );
        } else {
            Comparable currVal = minMap.get(groupKey).t1();
            if (currVal.compareTo(record[this.minMaxColumn]) < 0){
                minMap.put(groupKey, new Tuple<>( (Comparable<?>) record[this.minMaxColumn], record) );
            }
        }
    }

    @Override
    public boolean isIndexAggregationScan(){
        return true;
    }

    @Override
    public IndexAggregateScan asIndexAggregationScan(){
        return this;
    }

}
