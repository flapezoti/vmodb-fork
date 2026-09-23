package dk.ku.di.dms.vms.modb.api.query.enums;

public enum StatementTypeEnum {

    SELECT("SELECT"),

    INSERT_INTO("INSERT INTO"),

    VALUES("VALUES"),

    DELETE("DELETE"),

    UPDATE("UPDATE"),

    ORDER_BY("ORDER BY"),

    GROUP_BY("GROUP BY"),

    SET("SET"),

    AS("AS"),

    ON("ON"),

    WHERE("WHERE"),

    FROM("FROM");

    public final String name;

    StatementTypeEnum(){ this.name = name(); }

    StatementTypeEnum(String name) {
        this.name = name;
    }

}
