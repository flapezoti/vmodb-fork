package dk.ku.di.dms.vms.modb.common.transaction;

public interface ITransactionContext {

    default void release() { }

}
