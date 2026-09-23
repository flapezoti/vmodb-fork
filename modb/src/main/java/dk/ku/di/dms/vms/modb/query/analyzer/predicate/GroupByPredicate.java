package dk.ku.di.dms.vms.modb.query.analyzer.predicate;

import dk.ku.di.dms.vms.modb.api.query.enums.GroupByOperationEnum;
import dk.ku.di.dms.vms.modb.definition.ColumnReference;

public final class GroupByPredicate {

    private final ColumnReference columnReference;
    private final GroupByOperationEnum groupByOperation;

    public GroupByPredicate(ColumnReference columnReference, GroupByOperationEnum groupByOperation) {
        this.columnReference = columnReference;
        this.groupByOperation = groupByOperation;
    }

    public ColumnReference columnReference() {
        return columnReference;
    }

    public int columnPosition() {
        return columnReference.getColumnPosition();
    }

    public GroupByOperationEnum groupByOperation() {
        return groupByOperation;
    }
}
