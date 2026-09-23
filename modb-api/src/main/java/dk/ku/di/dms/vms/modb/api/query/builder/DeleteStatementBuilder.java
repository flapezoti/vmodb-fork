package dk.ku.di.dms.vms.modb.api.query.builder;

import dk.ku.di.dms.vms.modb.api.query.statement.DeleteStatement;

public class DeleteStatementBuilder extends AbstractStatementBuilder {

    private final DeleteStatement statement;

    public DeleteStatementBuilder() {
        this.statement = new DeleteStatement();
    }

    public WhereClauseBridge<DeleteStatement> from(String param) {
        this.statement.table = param;
        return new WhereClauseBridge<>(this.statement);
    }

}
