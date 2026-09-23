package dk.ku.di.dms.vms.modb.common.data_structure;

import java.util.Objects;

/**
 * Basic entry to represent pair of values
 *
 * @param <T1> value 1
 * @param <T2> value 2
 */
public final class Tuple<T1, T2> {

    public T1 t1;
    public T2 t2;

    public Tuple(T1 t1, T2 t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public T1 t1() {
        return t1;
    }

    public T2 t2() {
        return t2;
    }

    public static <T1,T2> Tuple<T1,T2> of(T1 t1, T2 t2){
        return new Tuple<>(t1,t2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return Objects.equals(t1, tuple.t1) && Objects.equals(t2, tuple.t2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(t1, t2);
    }
}