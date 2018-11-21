package com.lim.xyyutil.annotation;

import java.lang.annotation.*;

/**
 * @author qinhao
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AnimalName {

    String value() default "";
}
