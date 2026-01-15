package com.repos_alba.todo.user.validation;

import com.repos_alba.todo.user.dto.CreateUserRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, CreateUserRequest> {

    @Override
    public boolean isValid(CreateUserRequest request, ConstraintValidatorContext context) {
        if (request.getPassword() == null || request.getVerifyPassword() == null) {
            return true;
        }

        boolean matches = request.getPassword().equals(request.getVerifyPassword());

        if (!matches) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Las contraseñas no coinciden")
                    .addPropertyNode("verifyPassword")
                    .addConstraintViolation();
        }

        return matches;
    }
}
