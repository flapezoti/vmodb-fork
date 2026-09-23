package dk.ku.di.dms.vms.modb.index.interfaces;

public interface ReadWriteBufferIndex<K> extends ReadOnlyBufferIndex<K> {

    void insert(K key, long srcAddress);

    void update(K key, long srcAddress);

    void delete(K key);

}
