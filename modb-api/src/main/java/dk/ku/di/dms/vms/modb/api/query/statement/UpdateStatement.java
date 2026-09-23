package dk.ku.di.dms.vms.modb.api.query.statement;

import dk.ku.di.dms.vms.modb.api.query.clause.SetClauseElement;

import java.util.List;

public final class UpdateStatement extends AbstractStatement {

    public String table;

    public List<SetClauseElement> setClause;

    @Override
    public UpdateStatement asUpdateStatement() {
        return this;
    }

    @Override
    public StatementType getType() {
        return StatementType.UPDATE;
    }

}
