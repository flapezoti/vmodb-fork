package dk.ku.di.dms.vms.modb.common.schema;

import dk.ku.di.dms.vms.modb.common.constraint.ConstraintReference;
import dk.ku.di.dms.vms.modb.common.constraint.ForeignKeyReference;
import dk.ku.di.dms.vms.modb.common.type.DataType;

/**
 * The <code>VmsDataModel</code> record describes the schema of VmsTable.
 */
public final class VmsDataModel {

    // possibly a code might have more than a vms to execute
    // co-locate services
    public String vmsName;

    public String tableName;

    // identification of columns that form the primary key. all tables must have a primary key
    public int[] primaryKeyColumns;

    // the name of the columns
    public String[] columnNames;

    // the data types of the columns
    public DataType[] columnDataTypes;

    /**
     * Foreign key references within the same
     * virtual microservice application
     */
    public ForeignKeyReference[] foreignKeyReferences;

    // this can be outside, another vms
    // public ExternalForeignKeyReference[] externalForeignKeyReferences;

    // constraints, referred by column position
    public ConstraintReference[] constraintReferences;

    public VmsDataModel(){}

    public VmsDataModel(String vmsName, String tableName, int[] primaryKeyColumns, String[] columnNames, DataType[] columnDataTypes,
                        ForeignKeyReference[] foreignKeyReferences, ConstraintReference[] constraintReferences) {
        this.vmsName = vmsName;
        this.tableName = tableName;
        this.primaryKeyColumns = primaryKeyColumns;
        this.columnNames = columnNames;
        this.columnDataTypes = columnDataTypes;
        this.foreignKeyReferences = foreignKeyReferences;
        this.constraintReferences = constraintReferences;
    }

    public int findColumnPosition(String columnName){
        for(int i = 0; i < this.columnNames.length; i++){
            if(this.columnNames[i].contentEquals(columnName)) return i;
        }
        return -1;
    }

}