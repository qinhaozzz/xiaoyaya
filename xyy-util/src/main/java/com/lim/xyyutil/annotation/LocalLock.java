package com.lim.xyyutil.annotation;

import java.lang.annotation.*;

/**
 * @author qinhao
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LocalLock {

    String key() default "";

    /**
     * 失效时间
     * @return
     */
    int expire() default 5;
}
