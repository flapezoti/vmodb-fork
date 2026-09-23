package dk.ku.di.dms.vms.modb.api.annotations;

import dk.ku.di.dms.vms.modb.api.interfaces.IEntity;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
public @interface VmsForeignKey {

    Class<? extends IEntity<?>> table();

    String column();

}