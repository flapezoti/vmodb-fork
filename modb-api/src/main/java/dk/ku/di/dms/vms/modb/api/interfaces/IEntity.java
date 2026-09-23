package dk.ku.di.dms.vms.modb.api.interfaces;

import java.io.Serializable;

public interface IEntity<PK extends Serializable> {

    default PK getId() {
        throw new UnsupportedOperationException();
    }

}