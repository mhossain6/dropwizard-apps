package com.example.rest.validation;

import com.example.rest.provider.InvalidUserInputException;

public class EntityValidator {

    public boolean ValidateEntity(Long id) throws InvalidUserInputException {
        if (null == id || id <= 0)
            throw new InvalidUserInputException(400, "Description provided is null");
        return true;
    }
}
