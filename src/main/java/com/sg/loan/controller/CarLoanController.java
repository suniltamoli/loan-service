package com.sg.loan.controller;

import com.sg.loan.commons.LoanException;
import com.sg.loan.commons.LoanError;
import com.sg.loan.model.LoanRequest;
import com.sg.loan.services.CustomerLoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1")
public class CarLoanController {

    private final CustomerLoanService customerService;

    public CarLoanController(CustomerLoanService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/carloan/approval/{loan_ref_number}")
    public ResponseEntity<?> approveLoanAsFrontOfficer(@PathVariable(value = "loan_ref_number") String loanRefNumber) {
       LoanRequest loanRequest;
        try {
            loanRequest =  customerService.getLoanDetails(loanRefNumber);
        } catch (Exception e) {
            if(e instanceof LoanException) {
                LoanError loanError = new LoanError(((LoanException) e).getErroCode(), ((LoanException) e).getMessage());
                return new ResponseEntity<LoanError>(loanError, HttpStatus.BAD_REQUEST);
            } else {
                LoanError loanError = new LoanError(((LoanException) e).getErroCode(),  e.getMessage());
                return new ResponseEntity<LoanError>(loanError, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<LoanRequest>(loanRequest, HttpStatus.OK);
    }

}
