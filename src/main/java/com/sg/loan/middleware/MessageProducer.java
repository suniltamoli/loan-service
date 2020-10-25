package com.sg.loan.middleware;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sg.loan.model.LoanRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    private final KafkaTemplate kafkaTemplate;
    private final String topicName;
    private final ObjectMapper objectMapper;

    public MessageProducer(KafkaTemplate kafkaTemplate, @Value("${PRODUCER_TOPIC_NAME}") String topicName, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
        this.objectMapper = objectMapper;
    }

    public void publishMessage(LoanRequest loanRequest)  {
        try {
            String requestAsString = objectMapper.writeValueAsString(loanRequest);
            kafkaTemplate.send(topicName, requestAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
