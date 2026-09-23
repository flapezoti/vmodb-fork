package dk.ku.di.dms.vms.modb.api.query.statement;

import dk.ku.di.dms.vms.modb.api.query.clause.WhereClauseElement;

import java.util.ArrayList;
import java.util.List;

public sealed abstract class AbstractStatement implements IStatement
        permits SelectStatement, UpdateStatement, DeleteStatement {

    // should make it a string?
    // used to cache query plans. where clause only found in select, update, and delete
    public final StringBuilder SQL;

    public final List<WhereClauseElement> whereClause;

    public AbstractStatement() {
        this.SQL = new StringBuilder();
        this.whereClause = new ArrayList<>();
    }

    public AbstractStatement(StringBuilder SQL) {
        this.SQL = SQL;
        this.whereClause = new ArrayList<>();
    }

}
