package dk.ku.di.dms.vms.sdk.core.facade;

import dk.ku.di.dms.vms.modb.api.query.statement.SelectStatement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Basic interface of a repository proxy
 */
public interface IVmsRepositoryFacade {

    Object fetch(SelectStatement selectStatement, Type type);

    void insertAll(List<Object> entities);

    InvocationHandler asInvocationHandler();

    Object[] extractFieldValuesFromEntityObject(Object entityObject);

}
