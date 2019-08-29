package org.vaadin.cdidemo.views.login;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.vaadin.cdidemo.data.UserProfileHolder;
import org.vaadin.cdidemo.events.AlreadyLoggedInEvent;
import org.vaadin.cdidemo.events.LoginEvent;
import org.vaadin.cdidemo.events.NotLoggedInEvent;

import com.vaadin.cdi.ViewScoped;

@ViewScoped
public class LoginPresenter {

	@Inject
	private Logger logger;
	
	@Inject
	private UserProfileHolder userService;
	
	private LoginView view;

	@Inject
	private Event<LoginEvent> loginEvent;
	
	@Inject
	private Event<AlreadyLoggedInEvent> alreadyLoggedEvent;

	public void setView(LoginView login) {
		view = login;
	}

	public void login(String username, String password) {
		// Delegate login processing to Session scoped user service
		if (userService.passesLogin(username, password)) {
			loginEvent.fire(new LoginEvent(username));
		} else {
			view.showError("Login failed");
		}
	}

	public void handleLoggedIn() {
		if (userService.getUser() != null) {
			logger.warn("User is already logged in");
			alreadyLoggedEvent.fire(new AlreadyLoggedInEvent());
		}
	}	
}
