package org.vaadin.cdidemo.views.admin;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.vaadin.cdidemo.data.User;
import org.vaadin.cdidemo.data.UserListService;
import org.vaadin.cdidemo.data.UserProfileHolder;
import org.vaadin.cdidemo.events.NotLoggedInEvent;
import org.vaadin.cdidemo.views.main.MainView;

import com.vaadin.cdi.CDINavigator;

public class AdminPresenter implements Serializable {

    @Inject
    private Logger logger;

    @Inject
    private UserProfileHolder userService;

    @Inject
    private Event<NotLoggedInEvent> event;

    @Inject
    private UserListService userList;

    @Inject
    private CDINavigator nav;

    private AdminView view;

    // Executor is injected with @Resource annotation instead of @Inject
    @Resource
    ManagedExecutorService executor;

    public void setView(AdminView adminView) {
        view = adminView;
    }

    public void requestUpdateUsers() {
        // Since database calls may take long, they should be done async using
        // Executors for threading and futures.
        final CompletableFuture<Stream<User>> future = CompletableFuture
                .supplyAsync(() -> userList.getUsers(), executor);
        future.thenAccept(users -> {
            view.updateUsers(users);
        });
    }

    public void handlePrivilegesAndLoggedIn() {
        if (userService.getUser() != null && !userService.isAdmin()) {
            logger.warn("User does not have admin privileges: "
                    + userService.getUser().getName());
            nav.navigateTo(MainView.VIEW);
        } else if (userService.getUser() == null) {
            logger.warn("User is not logged in");
            event.fire(new NotLoggedInEvent());
        }
    }
}
