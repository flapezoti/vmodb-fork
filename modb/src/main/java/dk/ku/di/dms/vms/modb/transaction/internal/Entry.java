package dk.ku.di.dms.vms.modb.transaction.internal;

public final class Entry<K,V> {

    public final K key;
    public final V val;
    public volatile Entry<K,V> next;

    public Entry(K key, V val, Entry<K,V> next){
        this.key = key;
        this.val = val;
        this.next = next;
    }

    public K key(){
        return this.key;
    }

    public V val(){
        return this.val;
    }

    public Entry<K,V> next(){
        return this.next;
    }

}
