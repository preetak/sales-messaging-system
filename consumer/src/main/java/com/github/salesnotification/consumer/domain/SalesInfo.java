package com.github.salesnotification.consumer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SalesInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String messageType;
    private String salesNumber; 
	private String productType;
	private Double value; 
	private Integer numberSold; 
	private String operation; 
	private Double adjustment;	
	
}
