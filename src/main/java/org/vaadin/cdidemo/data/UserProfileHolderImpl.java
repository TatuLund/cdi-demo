package org.vaadin.cdidemo.data;

import java.io.Serializable;
import java.util.Objects;

import javax.inject.Inject;

import org.slf4j.Logger;

import com.vaadin.cdi.VaadinSessionScoped;
import com.vaadin.server.VaadinSession;

// This is a simplified demo of session scoped service, used by the application 
// In real life complex application you would probably use Shiro or JAAS for security
// filter implementation
@VaadinSessionScoped
public class UserProfileHolderImpl implements UserProfileHolder, Serializable {

    @Inject
    private Logger logger;

    @Inject
    private UserListService userList;

    private User loggedUser = null;

    @Override
    public boolean passesLogin(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        userList.getUser(username).ifPresent(user -> {
            if (user.getPassword().equals(password)) {
                loggedUser = user;
                logger.info("User logged in: " + user.getName());
            } else {
                logger.info("User used wrong password: " + user.getName());
            }
        });
        if (loggedUser == null) {
            logger.warn("Unknown user attempted to log in");
        }
        return loggedUser != null;
    }

    @Override
    public void logout() {
        logger.info("User logout: " + loggedUser.getName());
        loggedUser = null;
        // This is one of the preferred ways to log out and terminate
        // VaadinSession
        VaadinSession.getCurrent().getSession().invalidate();
    }

    @Override
    public User getUser() {
        return loggedUser;
    }

    @Override
    public boolean isAdmin() {
        if (loggedUser != null) {
            return loggedUser.isAdmin();
        } else {
            throw new IllegalStateException(
                    "Can't check user privileges when user not logged in");
        }
    }
}
