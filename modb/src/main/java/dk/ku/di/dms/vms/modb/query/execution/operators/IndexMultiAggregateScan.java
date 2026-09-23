package dk.ku.di.dms.vms.modb.query.execution.operators;

import dk.ku.di.dms.vms.modb.definition.key.IKey;
import dk.ku.di.dms.vms.modb.query.analyzer.predicate.GroupByPredicate;
import dk.ku.di.dms.vms.modb.query.execution.operators.sum.SumUtils;
import dk.ku.di.dms.vms.modb.transaction.TransactionContext;
import dk.ku.di.dms.vms.modb.transaction.multiversion.index.IMultiVersionIndex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Can be reused for the same query
 */
public final class IndexMultiAggregateScan extends AbstractSimpleOperator {

    private final List<GroupByPredicate> aggregations;
    private final IMultiVersionIndex index;
    private final List<Integer> projectionColumns;

    public IndexMultiAggregateScan(List<GroupByPredicate> aggregations,
                                   IMultiVersionIndex index,
                                   // only the ones that are not aggregation
                                   List<Integer> projectionColumns,
                                   int entrySize) {
        super(entrySize);
        this.aggregations = aggregations;
        this.index = index;
        this.projectionColumns = projectionColumns;
    }

    public List<Object[]> runAsEmbedded(TransactionContext txCtx, IKey key){
        Object[] baseRecord = new Object[this.projectionColumns.size() + this.aggregations.size()];
        IAggregation<?>[] operations = extractOperations();

        Iterator<Object[]> iterator = this.index.iterator(txCtx, key);
        Object[] nextRecord;
        while(iterator.hasNext()){
            nextRecord = iterator.next();
            this.compute( nextRecord, operations );
            int idx = 0;
            for (int pos : this.projectionColumns) {
                baseRecord[idx] = nextRecord[pos];
                idx++;
            }
        }

        // project
        int idx = 0;
        for(int i = this.projectionColumns.size(); i < baseRecord.length; i++){
            baseRecord[i] = operations[idx].get();
            idx++;
        }

        var list = new ArrayList<Object[]>();
        list.add(baseRecord);
        return list;
    }

    private IAggregation<?>[] extractOperations() {
        IAggregation<?>[] operations = new IAggregation[this.aggregations.size()];
        int idx = 0;
        for(GroupByPredicate agg : this.aggregations){
            switch (agg.groupByOperation()) {
                case SUM ->
                        operations[idx] = SumUtils.buildSumOperation(agg.columnReference().dataType);
                case COUNT -> operations[idx] = new SumUtils.CountOp();
                default ->
                    throw new IllegalArgumentException("Unknown aggregation operation: " + agg.groupByOperation());
            }
            idx++;
        }
        return operations;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void compute(Object[] record, IAggregation[] operations){
        for(int i = 0; i < this.aggregations.size(); i++){
            operations[i].accept(record[this.aggregations.get(i).columnPosition()]);
        }
    }

    @Override
    public boolean isIndexMultiAggregationScan(){
        return true;
    }

    @Override
    public IndexMultiAggregateScan asIndexMultiAggregationScan(){
        return this;
    }

    public IMultiVersionIndex index(){
        return this.index;
    }

}
