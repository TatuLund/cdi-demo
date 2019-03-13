package com.vaadin.training.javaee;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.vaadin.cdi.ViewScoped;

@ViewScoped
public class MainPresenter {

	@Inject
	private Logger logger;
	
	@Inject
	private UserService user;

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
		if (!user.isLoggedIn()) {
			logger.warn("User is not logged in");
			event.fire(new NotLoggedInEvent());
		}
	}

}
