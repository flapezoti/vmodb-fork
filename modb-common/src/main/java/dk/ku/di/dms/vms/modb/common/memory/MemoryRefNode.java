package dk.ku.di.dms.vms.modb.common.memory;

/**
 * An object that references a space allocated in
 * memory for a operator or any other class to use
 */
public class MemoryRefNode {

    public final long address;

    // size
    public final long bytes;

    public MemoryRefNode next;

    public MemoryRefNode(long address, long bytes){
        this.address = address;
        this.bytes = bytes;
    }

    public long address() {
        return this.address;
    }

    public long bytes() {
        return this.bytes;
    }

}