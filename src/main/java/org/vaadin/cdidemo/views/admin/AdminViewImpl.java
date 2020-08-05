package org.vaadin.cdidemo.views.admin;

import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.vaadin.cdidemo.VersionLabel;
import org.vaadin.cdidemo.data.User;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.UIDetachedException;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@CDIView(AdminView.VIEW)
public class AdminViewImpl extends VerticalLayout implements AdminView, View {

	@Inject
	private AdminPresenter presenter;
	
	@Inject
	private VersionLabel versionLabel;
	
	@Inject
	private Logger logger;	

	private VerticalLayout userContainer = new VerticalLayout();
	
	public AdminViewImpl() {
		setSizeFull();
	}

	@PostConstruct
	private void init() {
		presenter.setView(this);
		// Ask presenter to start process of updating user list
		presenter.requestUpdateUsers();
		userContainer.setSizeFull();
		Label loading = new Label();
		loading.setStyleName(ValoTheme.LABEL_SPINNER);
		userContainer.addComponent(loading);
		userContainer.setComponentAlignment(loading, Alignment.MIDDLE_CENTER);
		addComponents(userContainer,versionLabel);
		setComponentAlignment(versionLabel, Alignment.BOTTOM_RIGHT);
	}

	// Update users in ui.access() since this method will be called from
	// background thread, we will avoid using threads in View classes and
	// use them in Presenter or Model instead
	public void updateUsers(Stream<User> users) {		
		try {
			getUI().access(() -> {
				userContainer.removeAllComponents();
				users.forEach(user -> {
					UserForm userForm = new UserForm(user);
					userContainer.addComponent(userForm);
					userContainer.setComponentAlignment(userForm, Alignment.MIDDLE_CENTER);
				});
			});
		} catch (UIDetachedException e) {
			logger.info("Browser was closed, updates not pushed");
		}
	}
	
	@Override 
	public void enter(ViewChangeEvent event) {
		presenter.handlePrivilegesAndLoggedIn();
	}
}
