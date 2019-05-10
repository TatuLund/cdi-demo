package org.vaadin.cdidemo.views.login;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.vaadin.cdidemo.LoginEvent;
import org.vaadin.cdidemo.data.UserProfileHolder;

import com.vaadin.cdi.ViewScoped;

@ViewScoped
public class LoginPresenter {

	@Inject
	private Logger logger;
	
	@Inject
	private UserProfileHolder userService;
	
	private LoginView view;

	@Inject
	private Event<LoginEvent> event;

	public void setView(LoginView login) {
		view = login;
	}

	public void login(String username, String password) {
		// Delegate login processing to Session scoped user service
		if (userService.passesLogin(username, password)) {
			event.fire(new LoginEvent(username));
		} else {
			view.showError("Login failed");
		}
	}

}