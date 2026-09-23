package dk.ku.di.dms.vms.modb.api.query.clause;

import dk.ku.di.dms.vms.modb.api.query.enums.ExpressionTypeEnum;

public final class WhereClauseElement {

    private final String column;
    private final ExpressionTypeEnum expression;
    private final Object value;

    public WhereClauseElement(String column, ExpressionTypeEnum expression, Object value) {
        this.column = column;
        this.expression = expression;
        this.value = value;
    }

    public String column() {
        return this.column;
    }

    public ExpressionTypeEnum expression() {
        return this.expression;
    }

    public Object value() {
        return this.value;
    }

    public WhereClauseElement overwriteValue(Object value) {
        return new WhereClauseElement(column, expression, value);
    }

}

