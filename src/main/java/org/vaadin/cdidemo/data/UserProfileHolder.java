package org.vaadin.cdidemo.data;

public interface UserProfileHolder {

	User getUser();

	boolean isAdmin();

	void logout();

	boolean passesLogin(String username, String password);

}
