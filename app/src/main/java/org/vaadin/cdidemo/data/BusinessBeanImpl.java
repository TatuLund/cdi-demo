package org.vaadin.cdidemo.data;

import java.io.Serializable;

import com.vaadin.cdi.ViewScoped;

// Example of business bean
@ViewScoped
public class BusinessBeanImpl implements BusinessBean, Serializable {
    private String businessData = null;

    public BusinessBeanImpl() {
        this.businessData = "Business data";
    }

    @Override
    public String getBusinessData() {
        return businessData;
    }
}
