package dk.ku.di.dms.vms.modb.query.execution.operators.scan;

import dk.ku.di.dms.vms.modb.query.execution.operators.AbstractSimpleOperator;
import dk.ku.di.dms.vms.modb.transaction.multiversion.index.IMultiVersionIndex;

public abstract class AbstractScan extends AbstractSimpleOperator {

    protected final IMultiVersionIndex index;

    // index of the columns
    protected final int[] projectionColumns;

    public AbstractScan(int entrySize, IMultiVersionIndex index, int[] projectionColumns) {
        super(entrySize);
        this.index = index;
        this.projectionColumns = projectionColumns;
    }

    protected Object[] getProjection(Object[] record) {
        if(record.length == this.projectionColumns.length) return record;
        int j = 0;
        Object[] projection = new Object[this.projectionColumns.length];
        for (int projectionColumn : this.projectionColumns) {
            projection[j] = record[projectionColumn];
            j++;
        }
        return projection;
    }

    public IMultiVersionIndex index(){
        return this.index;
    }

}
