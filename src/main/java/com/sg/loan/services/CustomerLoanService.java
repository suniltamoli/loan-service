package com.sg.loan.services;

import com.sg.loan.commons.LoanException;
import com.sg.loan.model.LoanRequest;

public interface CustomerLoanService {

    LoanRequest getLoanDetails(String loanRefNumber) throws LoanException;
    LoanRequest saveCustomerData(LoanRequest loanRequest) throws LoanException;
}
