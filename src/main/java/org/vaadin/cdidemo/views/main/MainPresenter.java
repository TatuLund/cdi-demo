package org.vaadin.cdidemo.views.main;

import java.io.Serializable;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.vaadin.cdidemo.data.BusinessBean;
import org.vaadin.cdidemo.data.UserProfileHolder;
import org.vaadin.cdidemo.eventbus.EventBus;
import org.vaadin.cdidemo.eventbus.EventBus.EventBusListener;
import org.vaadin.cdidemo.events.NotLoggedInEvent;
import org.vaadin.cdidemo.rest.RestMessageEvent;

import com.vaadin.cdi.ViewScoped;

@ViewScoped
public class MainPresenter implements Serializable, EventBusListener {

    @Inject
    private Logger logger;

    @Inject
    private UserProfileHolder userService;

    @Inject
    private Event<NotLoggedInEvent> event;

    @Inject
    private BusinessBean businessBean;

    @Inject
    private EventBus eventBus;
    
    private MainView view;

    public void setView(MainView main) {
        view = main;
//        eventBus.registerEventListener(event -> {
//            RestMessageEvent ev = (RestMessageEvent) event;
//            view.showMessage(ev.getParameter());
//        });
        eventBus.registerEventBusListener(this);
    }

    public void requestUpdateBusLabel() {
        view.updateBusLabel(businessBean.getBusinessData());
    }

    public void handleLoggedIn() {
        if (userService.getUser() == null) {
            logger.warn("User is not logged in");
            event.fire(new NotLoggedInEvent());
        }
    }

    public void removeEventBusListener() {
        eventBus.unregisterEventBusListener(this);
        
    }

    @Override
    public void eventFired(Object event) {
        RestMessageEvent ev = (RestMessageEvent) event;
        view.showMessage(ev.getParameter());       
    }
 }
