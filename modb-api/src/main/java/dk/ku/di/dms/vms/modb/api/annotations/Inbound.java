package dk.ku.di.dms.vms.modb.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Inbound {

    String[] values();

    String precedence() default "";

    // this is across different events. but what about the case of one event triggering multiple methods?
    // should be defined at transactional level,
    // precedence properties. we still allow concurrent actions if they don't conflict with each other.
    // Partial order make sense. can use these confluence. if we don't allow confluent tasks to do not execute, it is bad.
    // if you don't define a dag, then it is confluent. don't need to reason about events, don't need to coordinate

}
