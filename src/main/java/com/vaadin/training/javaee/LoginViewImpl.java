package com.vaadin.training.javaee;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.vaadin.cdi.CDIView;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@CDIView(LoginView.VIEW)
public class LoginViewImpl extends VerticalLayout implements LoginView, View {

	@Inject
	private LoginPresenter presenter;

	@Inject
	VersionLabel label;
	
	@Inject
	Logger logger;
	
	public LoginViewImpl() {

		setSizeFull();

		final FormLayout form = new FormLayout();
		form.setSizeUndefined();
		addComponents(form);
		setComponentAlignment(form, Alignment.MIDDLE_CENTER);

		final TextField name = new TextField("Username");
		form.addComponent(name);

		final PasswordField password = new PasswordField("Password");
		form.addComponent(password);

		final Button login = new Button("Login");
		login.addStyleName(ValoTheme.BUTTON_PRIMARY);
		login.setClickShortcut(KeyCode.ENTER);
		form.addComponent(login);
		// Delegate login processing to presenter
		login.addClickListener(event -> presenter.login(name.getValue(), password.getValue()));
	}

	@PostConstruct
	private void init() {
		presenter.setView(this);
		// Version label is UIScoped, so this change is shown also on MainView
		// Injected components are not available in constructor, hence you need
		// to add them in PostContruct method
		label.setValue("version 1.0");
		addComponents(label);
		setComponentAlignment(label, Alignment.BOTTOM_RIGHT);
	}

	@Override
	public void showError(String error) {
		Notification.show(error, Type.ERROR_MESSAGE);
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}
}
