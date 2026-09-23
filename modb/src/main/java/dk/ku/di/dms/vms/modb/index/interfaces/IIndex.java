package dk.ku.di.dms.vms.modb.index.interfaces;

import dk.ku.di.dms.vms.modb.definition.Schema;
import dk.ku.di.dms.vms.modb.index.IIndexKey;
import dk.ku.di.dms.vms.modb.index.IndexTypeEnum;

public interface IIndex<K> {

    IIndexKey key();

    Schema schema();

    // columns that form the key to each record entry
    int[] columns();

    boolean containsColumn(int columnPos);

    /** information used by the planner to decide for the appropriate operator */
    IndexTypeEnum getType();

    int size();

    boolean exists(K key);

}
