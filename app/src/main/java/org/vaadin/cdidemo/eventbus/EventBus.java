package org.vaadin.cdidemo.eventbus;

public interface EventBus {

    public interface EventBusListener {
        public void eventFired(Object event);
    }   

    public void post(Object event);

    public void registerEventBusListener(EventBusListener listener);

    public void unregisterEventBusListener(EventBusListener listener);
}
