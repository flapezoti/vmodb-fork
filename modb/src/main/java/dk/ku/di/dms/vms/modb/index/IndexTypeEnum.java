package dk.ku.di.dms.vms.modb.index;

public enum IndexTypeEnum {

    UNIQUE, // for primary key, can be single or multiple column

    // non-unique

    NON_UNIQUE, // non-unique hash-based column set identifies several table

    // RANGE, FILTER, BLOOM?
}
