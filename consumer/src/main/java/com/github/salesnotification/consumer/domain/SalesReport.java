package com.github.salesnotification.consumer.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesReport {

	String productType;
	Integer totalSoldForProduct;
	Double totalValueForProduct;
}
