package dk.ku.di.dms.vms.modb.api.query.clause;

import dk.ku.di.dms.vms.modb.api.query.enums.ExpressionTypeEnum;
import dk.ku.di.dms.vms.modb.api.query.enums.GroupByOperationEnum;

public record HavingClauseElement<T extends Number>(
        GroupByOperationEnum operation,
        String column,
        ExpressionTypeEnum expression,
        T value) {}