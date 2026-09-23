package dk.ku.di.dms.vms.modb.storage.record;

import static dk.ku.di.dms.vms.modb.common.memory.MemoryUtils.UNSAFE;

/**
 * Just to maintain compatibility with existing code and avoid refactoring that are useless now
 */
public final class AppendOnlyBufferOld {

    private long address;

    private long nextOffset;

    private final long size;

    public AppendOnlyBufferOld(long address, long size) {
        this.nextOffset = address;
        this.size = size;
    }

    // move offset
    public void forwardOffset(long bytes){
        this.nextOffset += bytes;
    }

    public long address(){
        return this.address;
    }

    public long size(){
        return this.size;
    }

    public long nextOffset() {
        return this.nextOffset;
    }

    public void append(long srcAddress, long bytes){
        UNSAFE.copyMemory(null, srcAddress, null, nextOffset, bytes);
        this.nextOffset += bytes;
    }

    public void append(long value){
        UNSAFE.putLong(this.nextOffset, value);
        this.nextOffset += Long.BYTES;
    }

    public void append(int value){
        UNSAFE.putInt(this.nextOffset, value);
        this.nextOffset += Integer.BYTES;
    }

    public void append(float value){
        UNSAFE.putFloat(this.nextOffset, value);
        this.nextOffset += Float.BYTES;
    }

    public void append(double value){
        UNSAFE.putDouble(this.nextOffset, value);
        this.nextOffset += Float.BYTES;
    }

}
