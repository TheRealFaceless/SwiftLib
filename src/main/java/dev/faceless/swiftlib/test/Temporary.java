package dev.faceless.swiftlib.test;

import java.lang.annotation.*;

@Temporary
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.FIELD})
public @interface Temporary {
}
