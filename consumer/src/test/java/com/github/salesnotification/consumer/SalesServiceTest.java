package com.github.salesnotification.consumer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.github.salesnotification.consumer.domain.SalesInfo;
import com.github.salesnotification.consumer.repository.SalesRepository;
import com.github.salesnotification.consumer.service.SalesService;

public class SalesServiceTest {
	
	private SalesInfo salesInfo;
	
    @Mock
    SalesRepository salesRepository;
    
    static SalesService salesService;
    
    @SuppressWarnings("deprecation")
	
    
	
	@Test
	@DisplayName("Process TYPE1 Message and Maintain Counter")
	void testProcessMessageType1() {
		MockitoAnnotations.initMocks(this);
    	salesService=new SalesService(salesRepository);
		salesInfo=new SalesInfo();
		salesInfo.setSalesNumber("1");
		salesInfo.setProductType("P1");
		salesInfo.setMessageType("TYPE1");
		salesInfo.setValue(10.0);
		int counter=salesService.processMessage(salesInfo);
		Assertions.assertEquals(1,counter);
		
	}
	
	@Test
	@DisplayName("Process TYPE2 Message and Maintain Counter")
	void testProcessMessageType2() {
		MockitoAnnotations.initMocks(this);
    	salesService=new SalesService(salesRepository);
		SalesInfo salesInfo=new SalesInfo();
		salesInfo.setSalesNumber("1");
		salesInfo.setProductType("P1");
		salesInfo.setMessageType("TYPE2");
		salesInfo.setValue(10.0);
		salesInfo.setNumberSold(5);
		int counter=salesService.processMessage(salesInfo);
		Assertions.assertEquals(1,counter);
		
	}
	
	@Test
	@DisplayName("Process TYPE3 Message and Maintain Counter")
	void testProcessMessageType3() {
		MockitoAnnotations.initMocks(this);
    	salesService=new SalesService(salesRepository);
		SalesInfo salesInfo=new SalesInfo();
		salesInfo.setSalesNumber("1");
		salesInfo.setProductType("P1");
		salesInfo.setMessageType("TYPE3");
		salesInfo.setAdjustment(2.0);
		salesInfo.setOperation("ADD");
		int counter=salesService.processMessage(salesInfo);
		Assertions.assertEquals(1,counter);
		
	}
}
