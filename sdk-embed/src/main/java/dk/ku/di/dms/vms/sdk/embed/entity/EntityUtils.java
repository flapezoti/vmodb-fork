package dk.ku.di.dms.vms.sdk.embed.entity;

import dk.ku.di.dms.vms.modb.api.interfaces.IEntity;
import dk.ku.di.dms.vms.modb.common.type.DataType;
import dk.ku.di.dms.vms.modb.common.type.DataTypeUtils;
import dk.ku.di.dms.vms.modb.definition.Schema;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public final class EntityUtils {

    private EntityUtils(){}

    private static final MethodHandles.Lookup LOOKUP;
    static {
        LOOKUP = MethodHandles.lookup();
    }

    @SuppressWarnings("unchecked")
    public static <T> Constructor<T> getEntityConstructor(Class<T> entityClazz){
        for(var constructor : entityClazz.getDeclaredConstructors()){
            if(constructor.getParameters().length == 0){
                return (Constructor<T>) constructor;
            }
        }
        throw new RuntimeException("No default constructor found");
    }

    public static Map<String, VarHandle> getVarHandleFieldsFromCompositePk(Class<?> pkClazz) throws NoSuchFieldException, IllegalAccessException {
        MethodHandles.Lookup lookup_ = MethodHandles.privateLookupIn(pkClazz, LOOKUP);
        Field[] fields = pkClazz.getDeclaredFields();
        Map<String, VarHandle> fieldMap = new LinkedHashMap<>(fields.length);
        for(Field field : fields){
            fieldMap.put(
                    field.getName(),
                    lookup_.findVarHandle(
                            pkClazz,
                            field.getName(),
                            field.getType()
                    )
            );
        }
        return fieldMap;
    }

    public static Map<String, VarHandle> getVarHandleFieldFromPk(Class<?> parentClazz, Schema schema) throws NoSuchFieldException, IllegalAccessException {
        // usually the first, but to make sure lets do like this
        int pkColumn = schema.getPrimaryKeyColumns()[0];
        String pkColumnName = schema.columnNames()[pkColumn];
        Map<String, VarHandle> fieldMap = new HashMap<>(1);
        fieldMap.put(
                pkColumnName,
                LOOKUP.findVarHandle(
                        parentClazz,
                        pkColumnName,
                        parentClazz.getDeclaredField(pkColumnName).getType()
                )
        );
        return fieldMap;
    }

    public static Map<String, VarHandle> getVarHandleFieldsFromEntity(Class<? extends IEntity<?>> entityClazz, Schema schema)
            throws NoSuchFieldException, IllegalAccessException {
        MethodHandles.Lookup lookup_ = MethodHandles.privateLookupIn(entityClazz, LOOKUP);
        Map<String, VarHandle> fieldMap = new HashMap<>(schema.columnNames().length);
        int i = 0;
        Class<?> clazz;
        for(String columnName : schema.columnNames()){
            if(schema.columnDataTypes()[i] == DataType.ENUM){
                clazz = entityClazz.getDeclaredField(columnName).getType();
            } else {
                clazz = DataTypeUtils.getJavaTypeFromDataType(schema.columnDataTypes()[i]);
            }
            fieldMap.put(columnName, lookup_.findVarHandle(entityClazz, columnName, clazz) );
            i++;
        }
        return fieldMap;
    }

}
