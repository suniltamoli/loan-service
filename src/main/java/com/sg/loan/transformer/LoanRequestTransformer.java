package com.sg.loan.transformer;

import com.sg.loan.entity.LoanRequestEntity;
import com.sg.loan.model.LoanRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
public class LoanRequestTransformer {
    public LoanRequestEntity transformCustomerDetails(LoanRequest loanRequest) {
        LoanRequestEntity loanRequestEntity = new LoanRequestEntity();
        BeanUtils.copyProperties(loanRequest, loanRequestEntity);
        Timestamp approvedOnLoanOfficer = new Timestamp(System.currentTimeMillis());
        String approvedBy = "loan-officer";
        loanRequestEntity.setApprovedByLoanOfficer(approvedBy);
        loanRequestEntity.setApprovedOnLoanOfficer(approvedOnLoanOfficer);
        loanRequestEntity.setApprovedByFrontOfficer(loanRequest.getApprovedByFrontOfficer());
//        loanRequestEntity.setApprovedOnFrontOfficer(loanRequest.getApprovedOnFrontOfficer());
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(loanRequest.getApprovedOnFrontOfficer().toString());
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            loanRequestEntity.setApprovedOnFrontOfficer(timestamp);
        } catch(Exception e) { //this generic but you can control another types of exception
            // look the origin of excption
        }


//        loanRequestEntity.setLoanRefNumber("LOAN"+UUID.randomUUID().toString().replace("-", ""));
        return  loanRequestEntity;
    }
}
