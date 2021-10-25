package org.vaadin.cdidemo.beacon;

import java.util.Date;

public interface Beacon {

    public interface TimerListener {
        public void timeStampUpdated(Date dateTimeStamp);
    }
  
    void registerTimerListener(TimerListener listener);

    void unregisterTimerListener(TimerListener listener);
}
