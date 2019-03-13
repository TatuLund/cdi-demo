package com.vaadin.training.javaee;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.vaadin.cdi.ViewScoped;

@ViewScoped
public class LoginPresenter {

	@Inject
	private Logger logger;
	
	@Inject
	private UserService user;
	
	private LoginView view;

	@Inject
	private Event<LoginEvent> event;

	public void setView(LoginView login) {
		view = login;
	}

	public void login(String username, String password) {
		// Delegate login processing to Session scoped user service
		if (user.passesLogin(username, password)) {
			event.fire(new LoginEvent(username));
			logger.info("User: "+username+" logged in");
		} else {
			view.showError("Login failed");
			logger.warn("User: "+username+" login failure");
		}
	}

}
