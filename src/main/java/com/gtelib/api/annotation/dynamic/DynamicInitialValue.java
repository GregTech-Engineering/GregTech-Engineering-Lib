package com.gtelib.api.annotation.dynamic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DynamicInitialValue {

    String key();

    String typeKey() default "default";

    String en();

    String cn();

    String enComment() default "";

    String cnComment() default "";

    String easyValue();

    String normalValue();

    String expertValue();
}
