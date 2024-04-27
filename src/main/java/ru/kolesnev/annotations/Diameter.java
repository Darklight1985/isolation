package ru.kolesnev.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DiameterValidator.class)
@Documented
public @interface Diameter {

    String message() default "Диаметр не соответствует условному номанильному";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
