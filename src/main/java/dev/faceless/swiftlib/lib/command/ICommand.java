package dev.faceless.swiftlib.lib.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SuppressWarnings("unused")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ICommand {
    String name() default "";
    String permission() default "";
    String tabCompleter() default "";
    int cooldown() default 1;
    boolean noArgs() default false;
    boolean allArgs() default false;
}