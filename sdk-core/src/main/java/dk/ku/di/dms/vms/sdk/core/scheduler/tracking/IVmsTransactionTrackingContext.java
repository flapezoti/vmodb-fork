package dk.ku.di.dms.vms.sdk.core.scheduler.tracking;

public interface IVmsTransactionTrackingContext {

    default boolean isSimple() {
        return true;
    }

    default ComplexVmsTransactionTrackingContext asComplex(){
        throw new IllegalStateException("The object is not a complex task tracking");
    }

    default SimpleVmsTransactionTrackingContext asSimple(){
        throw new IllegalStateException("The object is not a simple task tracking");
    }

}
