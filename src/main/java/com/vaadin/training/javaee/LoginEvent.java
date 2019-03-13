package com.vaadin.training.javaee;

public class LoginEvent {
	String user;

	public LoginEvent(String userName) {
		this.user = userName;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
