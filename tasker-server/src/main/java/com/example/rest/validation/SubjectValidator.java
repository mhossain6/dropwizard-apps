package com.example.rest.validation;

import com.example.rest.provider.InvalidUserInputException;

public class SubjectValidator {
    public SubjectValidator() {

    }

    public boolean ValidateSubject(String inputSubject) throws InvalidUserInputException {
        if (null == inputSubject)
            throw new InvalidUserInputException(400, "Description provided is null");
        else if (inputSubject.length() > 250)
            throw new InvalidUserInputException(400, "Description provided exceeds Max length of 250");

        return true;
    }
}
