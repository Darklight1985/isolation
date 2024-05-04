package ru.kolesnev.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import ru.kolesnev.enums.NominalDiameter;

@RequiredArgsConstructor
public class DiameterValidator implements ConstraintValidator<Diameter, Integer> {
    @Override
    public void initialize(Diameter constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return NominalDiameter.checkDiameter(value);
    }
}
