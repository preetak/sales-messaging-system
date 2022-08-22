package com.github.salesnotification.consumer.service.consumer;



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.salesnotification.consumer.domain.SalesInfo;
import com.github.salesnotification.consumer.domain.SalesInfoDto;
import com.github.salesnotification.consumer.service.SalesService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Consumer {

    private static final String salesTopic = "${topic.name}";

    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;
    private final SalesService salesService;

    @Autowired
    public Consumer(ObjectMapper objectMapper, ModelMapper modelMapper, SalesService salesService) {
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        this.salesService = salesService;
    }

    @KafkaListener(topics = salesTopic)
    public void consumeMessage(String message) throws JsonProcessingException {
    	log.info("message consumed {}", message);
      try {
        SalesInfoDto salesInfoDto = objectMapper.readValue(message, SalesInfoDto.class);
        SalesInfo salesInfo = modelMapper.map(salesInfoDto, SalesInfo.class);
        int maxMessageCounterValue=salesService.processMessage(salesInfo);
        log.info("The counter value for Max Messages that can be processed in now: " + maxMessageCounterValue);
        
      }catch(Exception e) {
    	  log.error("Exception: " + e);
      }
    }
   

}
