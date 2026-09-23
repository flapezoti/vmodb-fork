package dk.ku.di.dms.vms.modb.common.constraint;

public record ExternalForeignKeyReference
        (String vmsName,
         String vmsTableName, // this is always part of the same virtual microservice
         String columnName) {}