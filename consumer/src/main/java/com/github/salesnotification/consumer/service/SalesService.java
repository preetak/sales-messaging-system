package com.github.salesnotification.consumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.salesnotification.consumer.domain.Sales;
import com.github.salesnotification.consumer.domain.SalesInfo;
import com.github.salesnotification.consumer.domain.SalesReport;
import com.github.salesnotification.consumer.domain.dto.SalesInfoDto;
import com.github.salesnotification.consumer.repository.SalesRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SalesService {

    private final SalesRepository salesRepository;
	private  List<Sales> salesList=new ArrayList();
	private  List<String> productTypesList=new ArrayList();
	
    private Integer messageCounter=0;
    private Integer messageMaxCounter=0;
   
    private enum MessageType{
    	TYPE1,
    	TYPE2,
    	TYPE3;
    	
    }
	
	private enum Operators{
		ADD,
		SUBTRACT,
		MULTIPLY
	}
	
	private static final int maxMessages =  50;
	private static final int perPageMessages = 6;
	
   @Autowired
    public SalesService(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

   public void logSalesInfo(SalesInfo salesInfo) {
        SalesInfo persistedSalesInfo = salesRepository.save(salesInfo);
    	
        log.info("sales details persisted {}", persistedSalesInfo);
        
    }
    
    //function to compute and print the sales for every 10 messages received.
    private void processComputeAndPrintSales() {
    	log.info("***********The Sales Report of 10 sales is as below****************");
    	log.info("Message Counter :"+ messageCounter);
    	List<SalesReport> salesReportList=new ArrayList<>();
    	log.info("ProductTypeList Size: "+ productTypesList.size());	
    	log.info("SalesList Size: " + salesList.size());
    	
    	for (String productType:productTypesList) {
    		 SalesReport report1=new SalesReport();
    		 double totalValueForProduct=0.0;
    	     int totalSoldForProduct=0;
			 
    	     List<Sales> filteredList=salesList.stream()
				.filter(sale->sale.getProductType().equals(productType))
				.collect(Collectors.toList());
			 
			 for(Sales sale:filteredList) {	
				    if(sale.getNumberSold() != null) {			    	
					totalValueForProduct=totalValueForProduct+(sale.getNumberSold()*sale.getValue());					
					totalSoldForProduct=totalSoldForProduct+sale.getNumberSold();	
				    }
			 }
					
				
			report1.setProductType(productType);
			report1.setTotalSoldForProduct(totalSoldForProduct);
			report1.setTotalValueForProduct(totalValueForProduct);
			salesReportList.add(report1);
    	}		
		for(SalesReport report2:salesReportList) {
			log.info("Product Type: "+ report2.getProductType());
			log.info("Total Number of Sales for the Product: "+report2.getTotalSoldForProduct());
			log.info("Total Value of the ProductType: "+ report2.getTotalValueForProduct());
		}
			
	}
    
    
    
    //function to compute adjustments
    private void processComputeAdjustments(SalesInfo salesInfo) {
     log.info("Sales List Size: "+ salesList.size());
	 salesList=salesList.stream()
	   .filter(sale->
	       	sale.getProductType().equals(salesInfo.getProductType()))            
			.map(sale1-> {
				sale1.setAdjustment(salesInfo.getAdjustment());
				sale1.setOperation(salesInfo.getOperation());
				if(salesInfo.getOperation() != null && salesInfo.getOperation().equals(Operators.ADD.toString())) {
					sale1.setValue(sale1.getValue()+salesInfo.getAdjustment());
				}
				else if(salesInfo.getOperation() != null && salesInfo.getOperation().equals(Operators.SUBTRACT.toString())) {
					sale1.setValue(sale1.getValue()-salesInfo.getAdjustment());
				}
				else if(salesInfo.getOperation() != null && salesInfo.getOperation().equals(Operators.ADD.toString())) {
					sale1.setValue(sale1.getValue()*salesInfo.getAdjustment());
				}
				return sale1;
			
			}).collect(Collectors.toList());
	 log.info("Update Sales List: "+ salesList);
		
	}
    
    
    //function to print adjustments
    private void processPrintAdjustments() {
    	log.info("***************Messages Total Received is 50 now. No more messages can be processed *******");
    	log.info("*********Below is the Report of Adjustments during the time the application was running*********");
    	log.info("Message Max Counter :"+ messageMaxCounter);
    	log.info("Sales List Size: " + salesList.size());
		for(Sales sales:salesList) {
			if(sales.getAdjustment() != null) {
				log.info("Sale Number: " + sales.getSalesNumber());
				log.info("Product Type: " + sales.getProductType());
				log.info("Product Value: "+ sales.getValue());
				log.info("Adjustment: " + sales.getAdjustment());
				log.info("Operation used for Adjustment: "+ sales.getOperation());
			}
			
		}
		
	}
    
    
    //function to store and update the sales hashmap.
    private void processStoreAndUpdateSales(SalesInfo salesInfo) {
		Sales sales=new Sales();
		sales.setProductType(salesInfo.getProductType());
		sales.setSalesNumber(salesInfo.getSalesNumber());
		sales.setNumberSold((salesInfo.getNumberSold()!= null)?salesInfo.getNumberSold():new Integer(1));
		sales.setValue(salesInfo.getValue());
    	processStoreProductType(salesInfo.getProductType());
    	salesList.add(sales);
		
	}
    
   

	//function to store the product types
    private void processStoreProductType(String productType) {
    	if(!productTypesList.contains(productType))
		productTypesList.add(productType);
		
	}
    
    
    //function to process the messages and maintain the counter
    
   public int processMessage(SalesInfo salesInfo) {
	   
	   //Log each sales details in store
	   logSalesInfo(salesInfo);
	   
	   if(messageMaxCounter == maxMessages) {
		   processPrintAdjustments();
		   return messageMaxCounter;
	   }
	   
	   if(messageCounter == perPageMessages) {
		   processComputeAndPrintSales();
		   messageCounter=0;
	   }
	   
	   
	   if((salesInfo.getMessageType().equals(MessageType.TYPE1.toString()) 
			   || salesInfo.getMessageType().equals(MessageType.TYPE2.toString()))){
		   processStoreAndUpdateSales(salesInfo);
		   
		   messageCounter++;
		   messageMaxCounter++;
	   }
	   
	   if(salesInfo.getMessageType().equals(MessageType.TYPE3.toString())) {
		   processComputeAdjustments(salesInfo);
		   messageCounter++;
		   messageMaxCounter++;
	   }
	   
	   return messageMaxCounter;
	   
   }
    


}
