package dk.ku.di.dms.vms.modb.api.query.enums;

public enum GroupByOperationEnum {

    AVG("AVG"),
    SUM("SUM"),
    COUNT("COUNT"),
    MIN("MIN"),
    MAX("MAX");

    public final String name;

    GroupByOperationEnum(String name) {
        this.name = name;
    }

}
