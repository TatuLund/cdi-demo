package org.vaadin.cdidemo.views.admin;

import org.vaadin.cdidemo.data.User;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class UserForm extends HorizontalLayout {
	
	public UserForm(User user) {
		TextField nameField = new TextField("Name");
		TextField passwordField = new TextField("Password");
		
		CheckBox adminField = new CheckBox("Admin");
		addComponents(nameField,passwordField,adminField);
		setExpandRatio(nameField, 3);
		setExpandRatio(passwordField, 3);
		setExpandRatio(adminField, 1);
		setComponentAlignment(adminField, Alignment.BOTTOM_RIGHT);
		setWidth("700px");
		setHeight("100px");
		addStyleName(ValoTheme.LAYOUT_CARD);
		
		Binder<User> binder = new Binder<>();
		binder.forField(nameField)
			.asRequired()
			.withValidator(new StringLengthValidator("4-10 characters please",4,10))
			.bind(User::getName, User::setName);
		binder.forField(passwordField)
			.asRequired()
			.withValidator(new StringLengthValidator("4-10 characters please",4,10))
			.bind(User::getPassword, User::setPassword);
		binder.forField(adminField)
			.bind(User::isAdmin, User::setAdmin);		
		
		binder.setBean(user);
		
	}
}
