package dk.ku.di.dms.vms.modb.query.execution.filter.types;

@FunctionalInterface
public interface TypedFunction<T> {

    boolean apply(T t);

}
