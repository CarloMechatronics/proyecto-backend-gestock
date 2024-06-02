package com.proyecto.gestock.constraints.nullablesizevalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NullableSizeValidator implements ConstraintValidator<NullableSize, String> {
    private int min;
    private int max;

    @Override
    public void initialize(NullableSize constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        return value.length() >= min && value.length() <= max;
    }
}
