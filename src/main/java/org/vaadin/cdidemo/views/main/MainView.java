package org.vaadin.cdidemo.views.main;

public interface MainView {

    public static final String VIEW = "main";

    public void updateBusLabel(String businessData);
    
    public void showMessage(String message);

}
