package dk.ku.di.dms.vms.modb.query.analyzer.predicate;

import dk.ku.di.dms.vms.modb.api.query.enums.ExpressionTypeEnum;
import dk.ku.di.dms.vms.modb.definition.ColumnReference;
import dk.ku.di.dms.vms.modb.definition.Table;

public final class WherePredicate implements Comparable<WherePredicate> {

    public final ColumnReference columnReference;
    public final ExpressionTypeEnum expression;

    public final Object value;

    public WherePredicate(ColumnReference columnReference, ExpressionTypeEnum expression, Object value) {
        this.columnReference = columnReference;
        this.expression = expression;
        this.value = value;
    }

    public Table getTable() {
        return columnReference.table;
    }

    public Integer getColumnPosition() {
        return columnReference.columnPosition;
    }

    @Override
    public int compareTo(WherePredicate o) {
        return Integer.compare(this.columnReference.columnPosition, o.columnReference.columnPosition);
    }
}
