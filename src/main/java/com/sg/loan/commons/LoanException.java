package com.sg.loan.commons;

import lombok.Data;

@Data
public class LoanException extends Exception {
    private final String message;
    private final String erroCode;

    public LoanException(String message, String erroCode) {
        super(message);
        this.message = message;
        this.erroCode = erroCode;
    }
}
