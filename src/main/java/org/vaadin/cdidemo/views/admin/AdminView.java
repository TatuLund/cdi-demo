package org.vaadin.cdidemo.views.admin;

import java.util.stream.Stream;

import org.vaadin.cdidemo.data.User;

public interface AdminView {

    public static final String VIEW = "admin";

    void updateUsers(Stream<User> users);
}
