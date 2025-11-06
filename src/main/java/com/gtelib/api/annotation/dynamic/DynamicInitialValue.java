package com.gtelib.api.annotation.dynamic;

import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Target;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Target(allowedTargets = AnnotationTarget.FIELD)
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
