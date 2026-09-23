package dk.ku.di.dms.vms.sdk.core.facade;

import dk.ku.di.dms.vms.modb.api.interfaces.IEntity;
import dk.ku.di.dms.vms.modb.api.interfaces.IRepository;
import dk.ku.di.dms.vms.modb.api.query.statement.SelectStatement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * The default repository facade does not contain references to DBMS components
 * since the application is not co-located with the MODB
 *
 * However, it could maintain a cache, consistent with the VMS state
 */
public class NetworkRepositoryFacade implements IVmsRepositoryFacade, InvocationHandler {

    private final Class<?> pkClazz;

    private final Class<? extends IEntity<?>> entityClazz;

    @SuppressWarnings({"unchecked"})
    public NetworkRepositoryFacade(Class<? extends IRepository<?,?>> repositoryClazz){

        Type[] types = ((ParameterizedType) repositoryClazz.getGenericInterfaces()[0]).getActualTypeArguments();

        this.entityClazz = (Class<? extends IEntity<?>>) types[1];
        this.pkClazz = (Class<?>) types[0];

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }


    @Override
    public Object fetch(SelectStatement selectStatement, Type type) {
        return null;
    }

    @Override
    public void insertAll(List<Object> entities) {

    }

    @Override
    public InvocationHandler asInvocationHandler() {
        return this;
    }

    @Override
    public Object[] extractFieldValuesFromEntityObject(Object entityObject) {
        return new Object[0];
    }

}
