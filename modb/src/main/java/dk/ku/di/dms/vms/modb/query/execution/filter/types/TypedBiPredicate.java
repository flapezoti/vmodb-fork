package dk.ku.di.dms.vms.modb.query.execution.filter.types;

/**
 * Same as BiPredicate, but without the need to express a second typed parameter
 * @param <T>
 */
@FunctionalInterface
public interface TypedBiPredicate<T> {

    boolean apply(T t1, T t2);

}
