package dk.ku.di.dms.vms.modb.common.constraint;

import static dk.ku.di.dms.vms.modb.common.constraint.ConstraintConstants.*;

public enum ConstraintEnum {

    // null and not null can apply to every type
    NOT_NULL(NULLABLE),

    NULL(NULLABLE),

    // applied to numbers
    POSITIVE_OR_ZERO(NUMBER),

    POSITIVE(NUMBER),

    NEGATIVE(NUMBER),

    NEGATIVE_OR_ZERO(NUMBER),

    MIN(NUMBER),

    MAX(NUMBER),

    // applied to char and strings
    NOT_BLANK(CHARACTER);

    public final String type;

    ConstraintEnum(String type) {
        this.type = type;
    }

}
