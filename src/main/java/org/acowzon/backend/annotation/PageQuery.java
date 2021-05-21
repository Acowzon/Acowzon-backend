package org.acowzon.backend.annotation;

import org.acowzon.backend.enums.QueryMethodEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.TYPE})
public @interface PageQuery {

    Class translateEntity() default Void.class;

    QueryMethodEnum[] acceptMethods();
}
