package dk.ku.di.dms.vms.modb.api.query.builder;

import dk.ku.di.dms.vms.modb.api.query.clause.SetClauseElement;
import dk.ku.di.dms.vms.modb.api.query.statement.UpdateStatement;

public class UpdateStatementBuilder extends AbstractStatementBuilder  {

    private final UpdateStatement statement;

    public UpdateStatementBuilder() {
        this.statement = new UpdateStatement();
    }

    public SetClause update(String param) {
        this.statement.table = param;
        return new SetClause(this.statement);
    }

    // test whether need to maintain state or not.
    // ie., not having a static class and referencing the statement all the way along the chain of calls
    public static class SetClause {

        private final UpdateStatement statement;

        protected SetClause(UpdateStatement selectStatement){
            this.statement = selectStatement;
        }

        public WhereClauseBridge<UpdateStatement> set(String param, Object value) {
            SetClauseElement setClauseElement = new SetClauseElement( param, value );
            this.statement.setClause.add(setClauseElement);
            return new WhereClauseBridge<>(statement); // return where or join
        }

    }

}
