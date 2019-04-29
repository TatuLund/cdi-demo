package views.main;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.vaadin.cdidemo.NotLoggedInEvent;
import org.vaadin.cdidemo.data.BusinessBean;
import org.vaadin.cdidemo.data.UserProfileHolder;

import com.vaadin.cdi.ViewScoped;

@ViewScoped
public class MainPresenter {

	@Inject
	private Logger logger;
	
	@Inject
	private UserProfileHolder userService;

	@Inject
	private Event<NotLoggedInEvent> event;
	
	@Inject
	private BusinessBean businessBean;
	
	private MainView view;
	
	public void setView(MainView main) {
		view = main;
	}

	public void requestUpdateBusLabel() {
		view.updateBusLabel(businessBean.getBusinessData());
	}
	
	public void handleLoggedIn() {
		if (userService.getUser() == null) {
			logger.warn("User is not logged in");
			event.fire(new NotLoggedInEvent());
		}
	}

}
