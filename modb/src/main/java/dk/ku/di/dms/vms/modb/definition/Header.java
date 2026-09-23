package dk.ku.di.dms.vms.modb.definition;

/**
 * The header of an entry in a buffer
 */
public final class Header {

    public static final int SIZE = Byte.BYTES;

    public static final boolean ACTIVE = true;
    public static final boolean INACTIVE = false;

    public static final byte ACTIVE_BYTE = 1;
    public static final byte INACTIVE_BYTE = 0;
}