package dk.ku.di.dms.vms.modb.index;

import dk.ku.di.dms.vms.modb.definition.key.IKey;

/**
 * Serves to identify uniquely an index or set of indexes.
 * Differently from {@link IKey}, which serves for identifying unique values or unique index entries
 */
public interface IIndexKey extends IKey {

}
