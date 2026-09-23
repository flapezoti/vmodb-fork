package dk.ku.di.dms.vms.modb.query.execution.operators.sum;

import dk.ku.di.dms.vms.modb.common.type.DataType;
import dk.ku.di.dms.vms.modb.query.execution.operators.IAggregation;

public final class SumUtils {

    public interface SumOperation<T> extends IAggregation<T> {
        default int asInt() {
            throw new IllegalStateException("Not applied");
        }
        default float asFloat() {
            throw new IllegalStateException("Not applied");
        }
        default double asDouble() {
            throw new IllegalStateException("Not applied");
        }
        default long asLong() {
            throw new IllegalStateException("Not applied");
        }
    }

    public static class CountOp implements IAggregation<Object> {
        private int count = 0;
        @Override
        public void accept(Object obj) {
            this.count++;
        }
        @Override
        public Integer get() {
            return this.count;
        }
    }

    public static class IntSumOp implements SumOperation<Integer> {
        private int sum = 0;
        @Override
        public void accept(Integer i) {
            this.sum += i;
        }

        @Override
        public Integer get() {
            return this.sum;
        }

        @Override
        public int asInt() {
            return this.sum;
        }
    }

    public static class FloatSumOp implements SumOperation<Float> {
        private float sum = 0;
        @Override
        public void accept(Float i) {
            this.sum += i;
        }

        @Override
        public Float get() {
            return this.sum;
        }

        @Override
        public float asFloat() {
            return this.sum;
        }
    }

    public static class DoubleSumOp implements SumOperation<Double> {
        private double sum = 0;
        @Override
        public void accept(Double i) {
            this.sum += i;
        }

        @Override
        public Double get() {
            return this.sum;
        }

        @Override
        public double asDouble() {
            return this.sum;
        }
    }

    // we need a class that performs the operation and maintains state
    // the interface should have a close() method. if sum, return. if count (have to consider distinct), return. if
    // a consumer may suffice. srcAddress, columnOffset
    // dynamically create. different from filter, this must maintain state, ok to create for each query

    public static SumOperation<?> buildSumOperation(DataType dataType){
        switch(dataType){
            case INT -> {
                return new IntSumOp();
            }
            case FLOAT -> {
                return new FloatSumOp();
            }
            case DOUBLE -> {
                return new DoubleSumOp();
            }
        }
        throw new RuntimeException("Unsupported data type: " + dataType);
    }

}
