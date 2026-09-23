package dk.ku.di.dms.vms.modb.common.memory;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public final class MemoryUtils {

    static {
        // initialize everything
        try {
            UNSAFE = getUnsafe();
            BUFFER_ADDRESS_ADDRESS_OFFSET = getByteBufferAddressOffset();
            DEFAULT_PAGE_SIZE = getPageSize();
            DEFAULT_NUM_BUCKETS = 10;
        } catch (Exception ignored) {}

    }

    public static jdk.internal.misc.Unsafe UNSAFE;

    private static long BUFFER_ADDRESS_ADDRESS_OFFSET;

    public static int DEFAULT_PAGE_SIZE;

    public static int DEFAULT_NUM_BUCKETS;

    /**
     * <a href="https://stackoverflow.com/questions/19047584/getting-virtual-memory-page-size-by-java-code">link</a>
     * @return page size
     */
    private static int getPageSize(){
        return UNSAFE.pageSize();
    }

    private static long getByteBufferAddressOffset() throws NoSuchFieldException {
        return UNSAFE.objectFieldOffset(Buffer.class.getDeclaredField("address"));
    }

    private static jdk.internal.misc.Unsafe getUnsafe() {
        Field unsafeField;
        try {
            unsafeField = Unsafe.class.getDeclaredField("theInternalUnsafe");
            unsafeField.setAccessible(true);
            return (jdk.internal.misc.Unsafe) unsafeField.get(null);
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static long getByteBufferAddress(ByteBuffer buffer) {
        return UNSAFE.getLong(buffer, BUFFER_ADDRESS_ADDRESS_OFFSET);
    }

    public static int nextPowerOfTwo(int number) {
        number--;
        number |= number >> 1;
        number |= number >> 2;
        number |= number >> 4;
        number |= number >> 8;
        number |= number >> 16;
        return number + 1;
    }

}
