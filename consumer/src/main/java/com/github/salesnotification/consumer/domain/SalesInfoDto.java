package com.github.salesnotification.consumer.domain;

import lombok.Data;
import lombok.Value;

@Data
public class SalesInfoDto {
	String messageType;
    String salesNumber; 
	String productType;
	Double value; 
	Integer numberSold; 
	String operation; 
	Double adjustment;
	
}
