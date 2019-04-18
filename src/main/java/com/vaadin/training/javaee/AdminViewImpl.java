package com.vaadin.training.javaee;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

@CDIView(AdminView.VIEW)
public class AdminViewImpl extends VerticalLayout implements AdminView, View {

	@Inject
	private AdminPresenter presenter;
	
	@Inject
	private VersionLabel versionLabel;
	
	@Inject
	private Logger logger;	

	public AdminViewImpl() {
		setSizeFull();
	}

	@PostConstruct
	private void init() {
		presenter.setView(this);
		List<User> users = presenter.getUserList();
		for (User user : users) {
			UserForm userForm = new UserForm(user);
			addComponent(userForm);
			setComponentAlignment(userForm, Alignment.MIDDLE_CENTER);
		}
		addComponents(versionLabel);
		setComponentAlignment(versionLabel, Alignment.BOTTOM_RIGHT);
	}
	
	@Override 
	public void enter(ViewChangeEvent event) {
		presenter.handlePrivilegesAndLoggedIn();
	}
}
