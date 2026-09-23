package dk.ku.di.dms.vms.modb.common.constraint;

public final class ForeignKeyReference {

    // this is always part of the same virtual microservice
    private String parentTableName;

    private String parentColumnName;

    private String localColumnName;

    // for json parsing
    public ForeignKeyReference(){}

    public ForeignKeyReference(String parentTableName, String parentColumnName, String localColumnName) {
        this.parentTableName = parentTableName;
        this.parentColumnName = parentColumnName;
        this.localColumnName = localColumnName;
    }

    public String parentTableName(){
        return this.parentTableName;
    }

    public String parentColumnName(){
        return this.parentColumnName;
    }

    public String localColumnName(){
        return this.localColumnName;
    }

}