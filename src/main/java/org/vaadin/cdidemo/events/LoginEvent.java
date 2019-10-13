package org.vaadin.cdidemo.events;

import java.io.Serializable;

public class LoginEvent implements Serializable {
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
