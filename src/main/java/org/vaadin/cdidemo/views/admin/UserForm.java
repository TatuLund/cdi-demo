package org.vaadin.cdidemo.views.admin;

import org.vaadin.cdidemo.data.User;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
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
        addComponents(nameField, passwordField, adminField);
        setExpandRatio(nameField, 3);
        setExpandRatio(passwordField, 3);
        setExpandRatio(adminField, 1);
        setComponentAlignment(adminField, Alignment.BOTTOM_RIGHT);
        setWidth("700px");
        setHeight("100px");
        addStyleName(ValoTheme.LAYOUT_CARD);

        nameField.setRequired(true);
        nameField.addValidator(new StringLengthValidator("4-10 characters please", 4, 10, false));
        passwordField.setRequired(true);
        passwordField.addValidator(new StringLengthValidator("4-10 characters please", 4, 10, false));

        BeanFieldGroup<User> binder = new BeanFieldGroup<>(User.class);
        binder.bind(nameField, "name");
        binder.bind(passwordField, "password");
        binder.bind(adminField, "admin");
        binder.setItemDataSource(new BeanItem<>(user));

    }
}
