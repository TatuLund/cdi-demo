package org.vaadin.cdidemo.util;

import com.vaadin.server.VaadinService;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.ui.UI;

public class DemoUtils {

    public static void sessionFixation() {
        // Chrome 80 does not support synchronous XHR during page dismissal
        // anymore
        // thus Push needs to be disabled during session re-initialization
        UI.getCurrent().getPushConfiguration().setPushMode(PushMode.DISABLED);
        VaadinService.reinitializeSession(VaadinService.getCurrentRequest());
        UI.getCurrent().getPushConfiguration().setPushMode(PushMode.AUTOMATIC);
    }
}
