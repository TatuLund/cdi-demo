package com.vaadin.training.javaee;

import java.util.List;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.vaadin.cdi.CDINavigator;

public class AdminPresenter {

	@Inject
	private Logger logger;
	
	@Inject
	private UserService userService;
	
	@Inject
	private Event<NotLoggedInEvent> event;

	@Inject 
	private UserList userList;
	
	@Inject
	private CDINavigator nav;
	
	private AdminView view;

	public void setView(AdminView adminView) {
		view = adminView;
	}

	public List<User> getUserList() {
		// TODO Auto-generated method stub
		return userList.getUsers();
	}
	
	public void handlePrivilegesAndLoggedIn() {
		if (userService.getUser() != null && !userService.isAdmin()) {
			logger.warn("User does not have admin privileges: "+userService.getUser().getName());
			nav.navigateTo(MainView.VIEW);
		} else if (userService.getUser() == null) {
			logger.warn("User is not logged in");
			event.fire(new NotLoggedInEvent());
		}
	}	
}
