package org.vaadin.cdidemo.rest;

public class RestMessageEvent {

    private String parameter;

    public RestMessageEvent(String parameter) {
        this.parameter = parameter;
    }
    
    public String getParameter() {
        return parameter;
    }
}
