package dk.ku.di.dms.vms.modb.api.query.clause;

import dk.ku.di.dms.vms.modb.api.query.enums.GroupByOperationEnum;

/**
 * The actual operation that should be carried out in this column
 */
public record GroupBySelectElement(String column,
                                   GroupByOperationEnum operation) {}