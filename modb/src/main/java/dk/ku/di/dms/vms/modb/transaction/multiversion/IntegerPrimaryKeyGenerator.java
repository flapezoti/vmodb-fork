package dk.ku.di.dms.vms.modb.transaction.multiversion;

import java.util.concurrent.atomic.AtomicInteger;

public class IntegerPrimaryKeyGenerator implements IPrimaryKeyGenerator<Integer> {

    private final AtomicInteger sequencer;

    public IntegerPrimaryKeyGenerator() {
        this.sequencer = new AtomicInteger(0);
    }

    @Override
    public Integer next() {
        return this.sequencer.addAndGet(1);
    }

}
