package dk.ku.di.dms.vms.modb.common.constraint;

public sealed class ConstraintReference permits ValueConstraintReference {

    public ConstraintEnum constraint;

    // for now only supporting one column, but a constraint may apply to several columns
    public int column;

    public ConstraintReference(){}

    public ConstraintReference(ConstraintEnum constraint, int column) {
        this.constraint = constraint;
        this.column = column;
    }

    public ValueConstraintReference asValueConstraint() {
        throw new IllegalStateException("This class is not a value-based constraint.");
    }

}
