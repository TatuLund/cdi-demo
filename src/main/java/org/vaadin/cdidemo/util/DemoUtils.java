package org.vaadin.cdidemo.util;

import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServletRequest;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.ui.UI;

public class DemoUtils {

    public static void sessionFixation() {
        // Chrome 80 does not support synchronous XHR during page dismissal
        // anymore
        // thus Push needs to be disabled during session re-initialization
        UI.getCurrent().getPushConfiguration().setPushMode(PushMode.DISABLED);
        // Java EE 8 includes Servlet 3.1, which gives possibility to use
        // lighter weight
        // changeSessionId approach. However, this does not work with Push
        // transport WebSocket
        // as Atmosphere does not yet support this. However works fine with
        // LONG_POLLING and WEBSOCKET_XHR
        VaadinServletRequest request = (VaadinServletRequest) VaadinService
                .getCurrentRequest();
        request.getHttpServletRequest().changeSessionId();
        // With Java EE 7
        // VaadinService.reinitializeSession(VaadinService.getCurrentRequest());
        // But works only with WEBSOCKET_XHR
        UI.getCurrent().getPushConfiguration().setPushMode(PushMode.AUTOMATIC);
    }
}
