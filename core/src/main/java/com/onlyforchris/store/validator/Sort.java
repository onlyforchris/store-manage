package com.onlyforchris.store.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author: Chris
 * @create: 2024-03-24 02:31
 **/
@Target({METHOD, FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = SortValidator.class)
public @interface Sort {
    String message() default "排序字段不支持";

    String[] accepts() default {"add_time", "id"};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

