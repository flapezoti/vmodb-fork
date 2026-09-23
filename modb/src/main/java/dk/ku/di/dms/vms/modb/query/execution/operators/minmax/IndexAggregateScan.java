package dk.ku.di.dms.vms.modb.query.execution.operators.minmax;

import dk.ku.di.dms.vms.modb.common.data_structure.Tuple;
import dk.ku.di.dms.vms.modb.definition.Schema;
import dk.ku.di.dms.vms.modb.query.execution.operators.scan.AbstractScan;
import dk.ku.di.dms.vms.modb.transaction.TransactionContext;
import dk.ku.di.dms.vms.modb.transaction.multiversion.index.IMultiVersionIndex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class IndexAggregateScan extends AbstractScan {

    protected final Schema schema;

    protected final int[] indexColumns;

    protected final int minMaxColumn;

    private final int limit;

    public IndexAggregateScan(int entrySize, IMultiVersionIndex index, int[] projectionColumns, Schema schema, int[] indexColumns, int minMaxColumn, int limit) {
        super(entrySize, index, projectionColumns);
        this.schema = schema;
        this.indexColumns = indexColumns;
        this.minMaxColumn = minMaxColumn;
        this.limit = limit;
    }

    public abstract List<Object[]> runAsEmbedded(TransactionContext txCtx);

    protected List<Object[]> project(Map<GroupByKey, Tuple<Comparable<?>,Object[]>> maxMap){
        int i = 0;
        List<Object[]> result = new ArrayList<>();
        for(var entry : maxMap.entrySet()){
            Object[] record = entry.getValue().t2();
            result.add(this.getProjection(record));
            i++;
            if(i == this.limit) break;
        }
        return result;
    }

}
