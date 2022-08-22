package com.github.salesnotification.producer.domain;

import lombok.Data;
import lombok.Value;

@Data
public class SalesInfo {
	String messageType;
	String salesNumber; 
	String productType;
	Double value; 
	Integer numberSold; 
	String operation; 
	Double adjustment;   
}
