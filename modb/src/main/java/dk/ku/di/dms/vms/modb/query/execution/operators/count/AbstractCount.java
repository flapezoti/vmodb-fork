package dk.ku.di.dms.vms.modb.query.execution.operators.count;

import dk.ku.di.dms.vms.modb.definition.key.IKey;
import dk.ku.di.dms.vms.modb.index.interfaces.ReadOnlyIndex;
import dk.ku.di.dms.vms.modb.query.execution.operators.AbstractMemoryBasedOperator;

public class AbstractCount extends AbstractMemoryBasedOperator {

    protected final ReadOnlyIndex<IKey> index;

    public AbstractCount(ReadOnlyIndex<IKey> index, int entrySize) {
        super(entrySize);
        this.index = index;
    }

    /**
     * Only used by count operator
     */
    protected void append( int count ) {
        ensureMemoryCapacity();
        this.currentBuffer.append(1); // number of rows
        this.currentBuffer.append(count);
    }

}
