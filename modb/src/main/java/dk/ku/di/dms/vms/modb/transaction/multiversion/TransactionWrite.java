package dk.ku.di.dms.vms.modb.transaction.multiversion;

public final class TransactionWrite {

    private static final Object[] DUMB = new Object[0];

    public final WriteType type; // if delete operation, record is not necessary
    public final Object[] record; // the whole record

    public static TransactionWrite upsert(WriteType type, Object[] record) {
        return new TransactionWrite(type, record);
    }

    public static TransactionWrite delete(WriteType type) {
        return new TransactionWrite(type, DUMB);
    }

    private TransactionWrite(WriteType type, Object[] record) {
        this.type = type;
        this.record = record;
    }

}
