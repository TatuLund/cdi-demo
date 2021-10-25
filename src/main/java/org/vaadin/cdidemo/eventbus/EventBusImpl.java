package org.vaadin.cdidemo.eventbus;

import java.io.Serializable;
import java.util.WeakHashMap;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;

/**
 * Super simple event bus to be used with CDI, e.g. as application scoped
 * broadcaster.
 */
@Named
@Singleton
public class EventBusImpl implements EventBus, Serializable {

    @Inject
    private Logger logger;

    private int interval = 10;

    /**
     * It is <em>VERY IMPORTANT</em> we use a weak hash map when registering
     * Vaadin components. Without it, this class would keep references to the UI
     * objects forever, causing a massive memory leak.
     */
    private final WeakHashMap<EventBusListener, Object> eventListeners = new WeakHashMap<>();

    public EventBusImpl() {
    }

    public void post(Object event) {
        synchronized (eventListeners) {
            logger.info("Listeners: " + eventListeners.size() + " eventBus: "
                    + this.toString());
            eventListeners.forEach((listener, o) -> {
                logger.info("Event fired");
                listener.eventFired(event);
            });
        }
    }

    public void registerEventBusListener(EventBusListener listener) {
        synchronized (eventListeners) {
            logger.info("EventBus: " + this.toString());
            eventListeners.put(listener, null);
        }
    }

    public void unregisterEventBusListener(EventBusListener listener) {
        synchronized (eventListeners) {
            eventListeners.remove(listener);
        }
    }
}
