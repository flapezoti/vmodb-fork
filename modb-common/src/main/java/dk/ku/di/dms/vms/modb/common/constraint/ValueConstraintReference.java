package dk.ku.di.dms.vms.modb.common.constraint;

public final class ValueConstraintReference extends ConstraintReference {

    public final Object value;

    public ValueConstraintReference(ConstraintEnum constraint, int column, Object value) {
        super(constraint, column);
        this.value = value;
    }

    public ValueConstraintReference asValueConstraint() {
        return this;
    }

}
