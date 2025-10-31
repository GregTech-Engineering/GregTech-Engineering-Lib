package com.gtelib.api.annotation.language;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RegisterLanguage {

    String namePrefix() default "";

    String valuePrefix() default "";

    String key() default "";

    String en();

    String cn();
}
