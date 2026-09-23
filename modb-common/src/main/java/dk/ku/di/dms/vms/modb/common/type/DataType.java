package dk.ku.di.dms.vms.modb.common.type;

public enum DataType {

    BOOL(Byte.BYTES),

    INT(Integer.BYTES),

    CHAR(Character.BYTES),

    // stored as char array of fixed size
    STRING(Character.BYTES * Constants.DEFAULT_MAX_SIZE_STRING),

    LONG(Long.BYTES),

    FLOAT(Float.BYTES),

    DOUBLE(Double.BYTES),

    DATE(Long.BYTES),

    // should only be used by events
    STRING_ARRAY(Constants.DEFAULT_MAX_SIZE_STRING),

    FLOAT_ARRAY(Float.BYTES * Constants.MAX_ARRAY_NUM_ITEMS),

    INT_ARRAY(Integer.BYTES * Constants.MAX_ARRAY_NUM_ITEMS),

    // max 16 characters
    ENUM(Constants.DEFAULT_MAX_SIZE_STRING),

    // JSON
    COMPLEX(Character.BYTES * Constants.DEFAULT_MAX_SIZE_STRING);

    public final int value;

    DataType(int bytes) {
        this.value = bytes;
    }
}
