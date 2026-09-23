package dk.ku.di.dms.vms.modb.storage.iterator;

import dk.ku.di.dms.vms.modb.definition.key.IKey;
import dk.ku.di.dms.vms.modb.definition.key.IntKey;

/**
 * Just to avoid arbitrary creation of key objects
 * Particularly important for scan and aggregate operators
 * The reasoning is that since the design is single thread,
 * no synchronization is necessary to pick a key.
 * TODO create a buffer so multiple read-only queries can use
 * the shared buffer to obtain a key
 */
public abstract class CachingKeyIterator {

    private final IntKey keyForReuse = IntKey.of();

    protected IKey keyOf(int hash){
        this.keyForReuse.newValue( hash );
        return this.keyForReuse;
    }

}
