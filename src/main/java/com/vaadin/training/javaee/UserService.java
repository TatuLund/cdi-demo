package com.vaadin.training.javaee;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.vaadin.cdi.VaadinSessionScoped;
import com.vaadin.server.VaadinSession;

// This is a simplified demo of session scoped service, used by the application 
// In real life application you would probably use Shiro or JAAS for security
// filter implementation
@VaadinSessionScoped
public class UserService {
	
	@Inject
	private Logger logger;
	
	boolean loggedIn = false;
	
	public boolean passesLogin(String username, String password) {
		boolean passes = username.equals("demo") && password.equals("demo");
		if (passes) loggedIn = true;
		return passes;
	}
	
	public void logout() {
		loggedIn = false;
		logger.info("Performing logout");
		// This is one of the preferred ways to log out and terminate VaadinSession
		VaadinSession.getCurrent().getSession().invalidate();
	}
	
	public boolean isLoggedIn() {
		logger.info("Access check: "+loggedIn);
		return loggedIn;
	}

}
