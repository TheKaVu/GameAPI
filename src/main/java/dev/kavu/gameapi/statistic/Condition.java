package dev.kavu.gameapi.statistic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Condition {
    // Determines if the function or field should be treated as a condition

    boolean negate() default false;
    boolean alternative() default false;
}
