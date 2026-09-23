package dk.ku.di.dms.vms.modb.api.query.builder;

import dk.ku.di.dms.vms.modb.api.query.statement.InsertStatement;

/**
 * Looks into the object to extract the values
 */
public class InsertStatementBuilder {

    private final InsertStatement statement;

    public InsertStatementBuilder() {
        this.statement = new InsertStatement();
    }

    public IntoClauseBridge insert(Object... params){
        this.statement.values = params;
        return new IntoClauseBridge();
    }

    public class IntoClauseBridge {

        private IntoClauseBridge(){}

        public IQueryBuilder<InsertStatement> into(String table){
            statement.table = table;
            return () -> statement;
        }

    }

}
