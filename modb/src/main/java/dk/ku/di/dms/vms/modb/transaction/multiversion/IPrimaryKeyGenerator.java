package dk.ku.di.dms.vms.modb.transaction.multiversion;

public interface IPrimaryKeyGenerator<T extends Number> {

    T next();

}
