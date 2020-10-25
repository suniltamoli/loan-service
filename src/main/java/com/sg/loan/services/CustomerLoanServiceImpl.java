package com.sg.loan.services;

import com.sg.loan.commons.LoanException;
import com.sg.loan.entity.LoanRequestEntity;
import com.sg.loan.commons.Validator;
import com.sg.loan.middleware.MessageProducer;
import com.sg.loan.model.LoanRequest;
import com.sg.loan.repository.CustomerLoanRepository;
import com.sg.loan.transformer.LoanRequestTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
public class CustomerLoanServiceImpl implements CustomerLoanService {
    private CustomerLoanRepository customerRepository;
    private LoanRequestTransformer transformer;
    private Validator validator;
    private final MessageProducer messageProducer;

    public CustomerLoanServiceImpl(CustomerLoanRepository customerRepository, LoanRequestTransformer transformer, Validator validator, MessageProducer messageProducer) {
        this.customerRepository = customerRepository;
        this.transformer = transformer;
        this.validator = validator;
        this.messageProducer = messageProducer;
    }

    @Override
    public LoanRequest saveCustomerData(LoanRequest loanRequest) throws LoanException {
        try {
            validator.validateRequest(loanRequest);
            loanRequest.setLoanStatus("Approved");
            //may be call some third party api first
            LoanRequestEntity loanRequestEntity = customerRepository.save(transformer.transformCustomerDetails(loanRequest));
            loanRequest.setApprovedByLoanOfficer(loanRequestEntity.getApprovedByLoanOfficer());
            loanRequest.setApprovedOnLoanOfficer(loanRequestEntity.getApprovedOnLoanOfficer());
            messageProducer.publishMessage(loanRequest);
        } catch (Exception e) {
            if(e instanceof DataIntegrityViolationException) {
                log.error(e.getMessage());
                throw new LoanException(e.getMessage(), "200001");
            } else {
                log.error(e.getMessage());
                throw e;
            }
        }
        return loanRequest;

    }

    @Override
    public LoanRequest getLoanDetails(String loanRefNumber) throws LoanException {
        List<LoanRequestEntity> loanRequestEntities =  customerRepository.getCustomerByloanRefNumberr(loanRefNumber);
        LoanRequest loanRequest = new LoanRequest();
        if(!loanRequestEntities.isEmpty()) {
            BeanUtils.copyProperties(loanRequestEntities.get(0), loanRequest);
        } else {
            throw new LoanException("Request not found", "5000001");
        }
        return loanRequest;
    }

}
