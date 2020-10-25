package com.sg.loan.middleware;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sg.loan.commons.LoanException;
import com.sg.loan.model.LoanRequest;
import com.sg.loan.services.CustomerLoanService;
import javafx.fxml.LoadException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
    private final CustomerLoanService customerService;
    private final ObjectMapper objectMapper;

    public MessageConsumer(CustomerLoanService customerService, ObjectMapper objectMapper) {
        this.customerService = customerService;
        this.objectMapper = objectMapper;
    }


    @KafkaListener(topics = "${CONSUMER_TOPIC_NAME}")
    public void consumeMessage(String message) throws JsonProcessingException, LoadException, LoanException {
        LoanRequest loanRequest = objectMapper.readValue(message, LoanRequest.class);
        customerService.saveCustomerData(loanRequest);
    }
}
