package com.tema.sda.Tema_SDA_3.custom.annotations;


import com.tema.sda.Tema_SDA_3.custom.validation.CriticalFieldsValidationImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CriticalFieldsValidationImpl.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CriticalFieldsValidation {

    String message() default "Invalid phone number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
