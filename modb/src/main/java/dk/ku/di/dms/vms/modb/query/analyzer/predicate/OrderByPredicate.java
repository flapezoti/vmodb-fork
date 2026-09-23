package dk.ku.di.dms.vms.modb.query.analyzer.predicate;

import dk.ku.di.dms.vms.modb.api.query.enums.OrderBySortOrderEnum;
import dk.ku.di.dms.vms.modb.definition.ColumnReference;

public final class OrderByPredicate {

    public final ColumnReference columnReference;
    public final OrderBySortOrderEnum sortOperation;

    public OrderByPredicate(ColumnReference columnReference, OrderBySortOrderEnum sortOperation) {
        this.columnReference = columnReference;
        this.sortOperation = sortOperation;
    }

    public ColumnReference columnReference() {
        return columnReference;
    }

    public OrderBySortOrderEnum sortOperation() {
        return sortOperation;
    }
}
