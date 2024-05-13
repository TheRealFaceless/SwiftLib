package dev.faceless.swiftlib.test;

import java.lang.annotation.*;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface Temporary {
    String value() default "This method/field is temporary and should be removed before production.";
}
