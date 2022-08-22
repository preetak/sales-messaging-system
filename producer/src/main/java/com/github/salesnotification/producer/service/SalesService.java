package com.github.salesnotification.producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.salesnotification.producer.domain.SalesInfo;
import com.github.salesnotification.producer.service.producer.Producer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SalesService {

    private final Producer producer;

    @Autowired
    public SalesService(Producer producer) {
        this.producer = producer;
    }

    public String createSalesInfo(SalesInfo SalesInfo) throws JsonProcessingException {
        return producer.sendMessage(SalesInfo);
    }
}
