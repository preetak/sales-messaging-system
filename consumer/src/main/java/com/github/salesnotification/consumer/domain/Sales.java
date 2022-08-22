package com.github.salesnotification.consumer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
public class Sales {

	
    String salesNumber; 
	String productType;
	Double value; 
	Integer numberSold; 
	String operation; 
	Double adjustment;	
}