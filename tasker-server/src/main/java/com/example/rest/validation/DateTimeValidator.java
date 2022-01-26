package com.example.rest.validation;

import com.example.rest.provider.InvalidUserInputException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeValidator {

    public DateTimeValidator() {
    }

    public boolean ValidateDate(String inputDate) throws InvalidUserInputException {
        SimpleDateFormat simpleDateFmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = simpleDateFmt.parse(inputDate);
            return true;
        } catch (Exception e) {
            throw new InvalidUserInputException(400, "Date provided does not conform to 'yyyy-MM-dd' ISO format");
        }
    }

}
