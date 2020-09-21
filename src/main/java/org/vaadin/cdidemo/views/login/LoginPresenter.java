package org.vaadin.cdidemo.views.login;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.vaadin.cdidemo.beacon.Beacon;
import org.vaadin.cdidemo.beacon.Beacon.TimerListener;
import org.vaadin.cdidemo.data.UserProfileHolder;
import org.vaadin.cdidemo.events.AlreadyLoggedInEvent;
import org.vaadin.cdidemo.events.LoginEvent;
import org.vaadin.cdidemo.util.DemoUtils;

import com.vaadin.cdi.ViewScoped;
import com.vaadin.server.VaadinSession;

@ViewScoped
public class LoginPresenter implements Serializable, TimerListener {

	@Inject
	private Logger logger;
	
	@Inject
	private UserProfileHolder userService;
	
	private LoginView view;

	@Inject
	private Event<LoginEvent> loginEvent;
	
	@Inject
	private Event<AlreadyLoggedInEvent> alreadyLoggedEvent;

	@Inject
	Beacon beacon;
	
	public void setView(LoginView login) {
		view = login;
	}

	public void login(String username, String password) {
		// Delegate login processing to Session scoped user service
		logger.info("Session id: "+VaadinSession.getCurrent().getSession().getId());
		if (userService.passesLogin(username, password)) {
			DemoUtils.sessionFixation();
			logger.info("New session id: "+VaadinSession.getCurrent().getSession().getId());
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

	@PostConstruct
	private void init() {
		beacon.registerTimerListener(this);
	}

	public void removeTimer() {
		beacon.unregisterTimerListener(this);
	}

	@Override
	public void timeStampUpdated(Date timeStamp) {
		view.setTimeStamp(timeStamp.toString());		
	}
}
