package com.tema.sda.Tema_SDA_3.validation.validation;

import com.tema.sda.Tema_SDA_3.validation.annotations.CriticalFieldsValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CriticalFieldsValidationImpl implements
        ConstraintValidator<CriticalFieldsValidation, String> {
    @Override
    public void initialize(CriticalFieldsValidation constraintAnnotation) {

    }

    @Override
    public boolean isValid(String fieldProperties, ConstraintValidatorContext context) {
        return null != fieldProperties && !fieldProperties.isEmpty() && !fieldProperties.isBlank();
    }
}
