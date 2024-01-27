package com.takeaway.employee.domain.exceptions;

public class UniqueEmailException extends RuntimeException {
    public UniqueEmailException(String message) {
        super(message);
    }
}
