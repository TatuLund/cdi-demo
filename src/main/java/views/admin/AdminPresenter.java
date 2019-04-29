package views.admin;

import java.util.List;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.vaadin.cdidemo.NotLoggedInEvent;
import org.vaadin.cdidemo.data.User;
import org.vaadin.cdidemo.data.UserListService;
import org.vaadin.cdidemo.data.UserProfileHolder;

import com.vaadin.cdi.CDINavigator;

import views.main.MainView;

public class AdminPresenter {

	@Inject
	private Logger logger;
	
	@Inject
	private UserProfileHolder userService;
	
	@Inject
	private Event<NotLoggedInEvent> event;

	@Inject 
	private UserListService userList;
	
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
