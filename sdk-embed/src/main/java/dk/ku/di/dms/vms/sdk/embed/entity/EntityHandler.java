package dk.ku.di.dms.vms.sdk.embed.entity;

import dk.ku.di.dms.vms.modb.api.interfaces.IEntity;
import dk.ku.di.dms.vms.modb.common.type.DataType;
import dk.ku.di.dms.vms.modb.definition.Schema;

import java.io.Serializable;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.logging.Logger;

public class EntityHandler<PK extends Serializable, T extends IEntity<PK>> {

    private static final Logger LOGGER = Logger.getLogger(EntityHandler.class.getName());

    protected final Constructor<T> entityConstructor;

    protected final Map<String, VarHandle> entityFieldMap;

    protected final Map<String, VarHandle> pkFieldMap;

    private final Schema schema;

    public EntityHandler(Class<PK> pkClazz,
                         Class<T> entityClazz,
                         Schema schema) throws NoSuchFieldException, IllegalAccessException {
        this.schema = schema;
        this.entityConstructor = EntityUtils.getEntityConstructor(entityClazz);
        this.entityFieldMap = EntityUtils.getVarHandleFieldsFromEntity(entityClazz, this.schema);
        if(pkClazz.getPackageName().equalsIgnoreCase("java.lang") || pkClazz.isPrimitive()){
            this.pkFieldMap = EntityUtils.getVarHandleFieldFromPk(entityClazz, this.schema);
        } else {
            this.pkFieldMap = EntityUtils.getVarHandleFieldsFromCompositePk(pkClazz);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public final T parseObjectIntoEntity(Object[] object){
        try {
            // all entities must have default constructor
            T entity = this.entityConstructor.newInstance();
            int i;
            for(Map.Entry<String,VarHandle> entry : this.entityFieldMap.entrySet()){
                // must get the index of the column first
                i = this.schema.columnPosition(entry.getKey());
                if(object[i] == null){
                    continue;
                }
                try {
                    entry.getValue().set(entity, object[i]);
                } catch (ClassCastException e){
                    // has the entry come from raw index?
                    if(this.schema.columnDataType(i) == DataType.ENUM && object[i] instanceof String objStr && !objStr.isBlank()){
                        entry.getValue().set(entity, Enum.valueOf((Class)entry.getValue().varType(), objStr));
                    } else {
                        LOGGER.severe("Cannot cast column "+this.schema.columnName(i)+" for value "+object[i]);
                        throw new RuntimeException(e);
                    }
                }
            }
            return entity;
        } catch (ClassCastException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.severe(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Must be a linked sorted map. Ordered by the columns that appear on the key object.
     */
    protected final Object[] extractFieldValuesFromKeyObject(PK keyObject) {
        Object[] values = new Object[this.pkFieldMap.size()];
        if(keyObject instanceof Number){
            values[0] = keyObject;
            return values;
        }
        int fieldIdx = 0;
        // get values from key object
        for(String columnName : this.pkFieldMap.keySet()){
            values[fieldIdx] = this.pkFieldMap.get(columnName).get(keyObject);
            fieldIdx++;
        }
        return values;
    }

    public final Object[] extractFieldValuesFromEntityObject(T entity) {
        Object[] values = new Object[this.schema.columnNames().length];
        int fieldIdx = 0;
        for(String columnName : this.schema.columnNames()){
            values[fieldIdx] = this.entityFieldMap.get(columnName).get(entity);
            fieldIdx++;
        }
        return values;
    }

}
