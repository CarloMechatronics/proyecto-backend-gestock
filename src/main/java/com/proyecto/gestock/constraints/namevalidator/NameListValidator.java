package com.proyecto.gestock.constraints.namevalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class NameListValidator implements ConstraintValidator<Name, List<String>> {
    NameValidator nameValidator = new NameValidator();
    @Override
    public boolean isValid(List<String> names, ConstraintValidatorContext context) {
        if (names == null || names.isEmpty())
            return true;

        for (String name : names)
            if (name == null || !nameValidator.isValid(name, context))
                return false;

        return true;
    }
}
