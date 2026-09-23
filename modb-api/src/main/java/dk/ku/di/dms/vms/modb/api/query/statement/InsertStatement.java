package dk.ku.di.dms.vms.modb.api.query.statement;

public class InsertStatement implements IStatement {

    public Object[] values;

    public String table;

    @Override
    public StatementType getType() {
        return StatementType.INSERT;
    }

    @Override
    public InsertStatement asInsertStatement() {
        return this;
    }

}
