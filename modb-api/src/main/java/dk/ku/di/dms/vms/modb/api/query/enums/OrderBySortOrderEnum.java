package dk.ku.di.dms.vms.modb.api.query.enums;

public enum OrderBySortOrderEnum {

    ASC("ASC"),
    DESC("DESC");

    public final String name;

    OrderBySortOrderEnum(){
        this.name = name();
    }

    OrderBySortOrderEnum(String name) {
        this.name = name;
    }

}
