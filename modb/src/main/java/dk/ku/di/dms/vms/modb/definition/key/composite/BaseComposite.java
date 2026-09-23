package dk.ku.di.dms.vms.modb.definition.key.composite;

public abstract class BaseComposite {

    private final int hash;

    public BaseComposite(int hash){
        this.hash = hash;
    }

    @Override
    public int hashCode() {
        return this.hash;
    }

}
