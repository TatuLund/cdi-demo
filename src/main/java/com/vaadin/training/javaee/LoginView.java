package com.vaadin.training.javaee;

public interface LoginView {

	// Good pattern to define and maintain view name in one place
	// We do it in interface, since we do not want to inject LoginViewImpl
	public static final String VIEW = "login";
		
	public void showError(String error);
}
