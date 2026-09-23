package dk.ku.di.dms.vms.modb.api.query.builder;

import dk.ku.di.dms.vms.modb.api.query.statement.IStatement;

public interface IQueryBuilder<T extends IStatement> {
    T build();
}
