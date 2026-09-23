package dk.ku.di.dms.vms.modb.definition;

import dk.ku.di.dms.vms.modb.common.type.DataType;

public final class ColumnReference {

    // used to set the value of the attribute of a return class type
    // probably can be removed since we can use the column index order
    public final String columnName;

    public final int columnPosition;

    public final DataType dataType;

    public final Table table;

    public ColumnReference(String columnName, int columnPosition, Table table) {
        this.columnName = columnName;
        this.columnPosition = columnPosition;
        this.dataType = table.schema().columnDataType(columnPosition);
        this.table = table;
    }

    public ColumnReference(String columnName, Table table) {
        this.columnName = columnName;
        this.columnPosition = table.schema().columnPosition( columnName );
        this.dataType = table.schema().columnDataType(columnPosition);
        this.table = table;
    }

    public int getColumnPosition() {
        return this.columnPosition;
    }

}
