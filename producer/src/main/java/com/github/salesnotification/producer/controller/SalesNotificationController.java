package com.github.salesnotification.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.salesnotification.producer.domain.SalesInfo;
import com.github.salesnotification.producer.service.SalesService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/sales")
public class SalesNotificationController {

    private final SalesService salesService;

    @Autowired
    public SalesNotificationController(SalesService salesService) {
        this.salesService = salesService;
    }

    @PostMapping
    public String createSalesInfo(@RequestBody SalesInfo SalesInfo) throws JsonProcessingException {
        log.info("sales info added to topic");
        return salesService.createSalesInfo(SalesInfo);
    }
}
