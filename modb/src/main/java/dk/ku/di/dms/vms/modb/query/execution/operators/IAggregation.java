package dk.ku.di.dms.vms.modb.query.execution.operators;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface IAggregation<T> extends Consumer<T>, Supplier<T> {
}
