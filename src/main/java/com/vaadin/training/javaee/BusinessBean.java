package com.vaadin.training.javaee;

import com.vaadin.cdi.ViewScoped;

// Example of business bean
@ViewScoped
public class BusinessBean {
	private String businessData = null;
		
	public BusinessBean() {
		this.businessData = "Business data";
	}
	
	public String getBusinessData() {
		return businessData;
	}
}
