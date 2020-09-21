package org.vaadin.cdidemo.beacon;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.atmosphere.inject.annotation.ApplicationScoped;
import org.slf4j.Logger;

@ApplicationScoped
public class BeaconImpl implements Beacon, Serializable {

	@Inject
	private Logger logger;

	@Resource
	ManagedExecutorService executor;

	private int interval = 10;

	/**
	 * It is <em>VERY IMPORTANT</em> we use a weak hash map when registering
	 * Vaadin components. Without it, this class would keep references to the UI
	 * objects forever, causing a massive memory leak.
	 */
	private final WeakHashMap<TimerListener, Object> listeners = new WeakHashMap<>();

	private Future<?> timerFuture;

	public BeaconImpl() {
	}

	private void postTime() {
		Date date;
		while (true) {
			try {
				Thread.sleep(interval * 1000);
			} catch (InterruptedException e) {
				logger.error("Timer thread interrupted");
			}
			date = new Date();
			listeners.forEach((listener, o) -> listener.timeStampUpdated(new Date()));
		}

	}

	public void registerTimerListener(TimerListener listener) {
		synchronized (listeners) {
			listeners.put(listener, null);

			// If the thread isn't running, start a new one.
			if (timerFuture == null || timerFuture.isDone() || timerFuture.isCancelled()) {
				timerFuture = executor.submit(this::postTime);
				logger.info("Timer started");
			}
		}
	}

	public void unregisterTimerListener(TimerListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);

			// if there are no listeners, stop the thread
			if (listeners.isEmpty() && timerFuture != null) {
				timerFuture.cancel(true);
				timerFuture = null;
				logger.info("Timer stopped");
			}
		}
	}
	
}
