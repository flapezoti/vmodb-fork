package dk.ku.di.dms.vms.modb.api.query.statement;

public interface IStatement {

    StatementType getType();

    default SelectStatement asSelectStatement(){
        throw new IllegalStateException("Not implemented.");
    }

    default UpdateStatement asUpdateStatement(){
        throw new IllegalStateException("Not implemented.");
    }

    default DeleteStatement asDeleteStatement() {
        throw new IllegalStateException("Not implemented.");
    }

    default InsertStatement asInsertStatement(){
        throw new IllegalStateException("Not implemented.");
    }


}
