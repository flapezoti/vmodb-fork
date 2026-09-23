package dk.ku.di.dms.vms.modb.api.query.clause;

import dk.ku.di.dms.vms.modb.api.query.enums.ExpressionTypeEnum;
import dk.ku.di.dms.vms.modb.api.query.enums.JoinTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents the parsed structure of a user-defined JOIN operation
 */
public class JoinClauseElement {

    private enum JoinColumnCardinality{
        SIMPLE,
        COMPOSITE
    }

    public final String tableLeft;
    public final String tableRight;
    public final JoinTypeEnum joinType;

    public JoinColumnCardinality cardinality;

    /** SIMPLE */
    public String columnLeft;
    public String columnRight;
    public ExpressionTypeEnum expression;

    /** COMPOSITE */
    public List<String> columnsLeft;
    public List<String> columnsRight;
    public List<ExpressionTypeEnum> expressions;

    public JoinClauseElement(String tableLeft, String columnLeft, JoinTypeEnum joinType, ExpressionTypeEnum expression, String tableRight, String columnRight) {
        this.tableLeft = tableLeft;
        this.columnLeft = columnLeft;
        this.joinType = joinType;
        this.expression = expression;
        this.tableRight = tableRight;
        this.columnRight = columnRight;
        this.cardinality = JoinColumnCardinality.SIMPLE;

        this.columnsLeft = null;
        this.columnsRight = null;
        this.expressions = null;
    }

    /**
     * Primarily used for tests
     * @param tableLeft
     * @param columnsLeft
     * @param joinType
     * @param expressions
     * @param tableRight
     * @param columnsRight
     */
    public JoinClauseElement(String tableLeft, List<String> columnsLeft, JoinTypeEnum joinType, List<ExpressionTypeEnum> expressions, String tableRight, List<String> columnsRight) {
        this.tableLeft = tableLeft;
        this.columnLeft = null;
        this.joinType = joinType;
        this.expression = null;
        this.tableRight = tableRight;
        this.columnRight = null;
        this.cardinality = JoinColumnCardinality.COMPOSITE;

        this.columnsLeft = columnsLeft;
        this.columnsRight = columnsRight;
        this.expressions = expressions;
    }

    public void migrateToComposite( String columnLeft, ExpressionTypeEnum expression, String columnRight ){

        this.columnsLeft = new ArrayList<>(3);
        this.columnsRight = new ArrayList<>(3);
        this.expressions = new ArrayList<>(3);

        this.columnsLeft.add( this.columnLeft );
        this.columnsRight.add( this.columnRight );
        this.expressions.add( this.expression );

        this.cardinality = JoinColumnCardinality.COMPOSITE;

        this.columnLeft = null;
        this.columnRight = null;
        this.expression = null;

        this.columnsLeft.add( columnLeft );
        this.columnsRight.add( columnRight );
        this.expressions.add( expression );
    }

    public void addCondition( String columnLeft, ExpressionTypeEnum expression, String columnRight ){
        if( !isComposite() ){
            migrateToComposite( columnLeft, expression, columnRight );
            return;
        }
        this.columnsLeft.add( columnLeft );
        this.columnsRight.add( columnRight );
        this.expressions.add( expression );
    }

    public boolean isComposite() {
        return this.cardinality == JoinColumnCardinality.COMPOSITE;
    }

}