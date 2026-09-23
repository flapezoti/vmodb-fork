package dk.ku.di.dms.vms.modb.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation specifies an independent concurrency unit in the virtual microservice
 * <a href="https://stackoverflow.com/questions/44291122/how-do-i-pass-a-method-to-an-annotation-using-java-8">Annotation attribute cannot take a method</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PartitionBy {

    Class<?> clazz();
    String method();

}
