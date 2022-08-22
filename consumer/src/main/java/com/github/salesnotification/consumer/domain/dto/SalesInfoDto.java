package com.github.salesnotification.consumer.domain.dto;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class SalesInfoDto {
	String messageType;
	String salesNumber; 
	String productType;
	Double value; 
	Integer numberSold; 
	String operation; 
	Double adjustment;  
}
