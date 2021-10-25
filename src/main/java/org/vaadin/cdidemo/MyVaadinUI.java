package org.vaadin.cdidemo;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.slf4j.Logger;
import org.vaadin.cdidemo.data.UserProfileHolder;
import org.vaadin.cdidemo.events.AlreadyLoggedInEvent;
import org.vaadin.cdidemo.events.LoginEvent;
import org.vaadin.cdidemo.events.NotLoggedInEvent;
import org.vaadin.cdidemo.views.admin.AdminView;
import org.vaadin.cdidemo.views.login.LoginView;
import org.vaadin.cdidemo.views.main.MainView;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.cdi.CDINavigator;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.server.VaadinCDIServlet;
import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.Page;
import com.vaadin.server.SystemMessages;
import com.vaadin.server.SystemMessagesInfo;
import com.vaadin.server.SystemMessagesProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Theme("valo")
@SuppressWarnings("serial")
@PreserveOnRefresh
@Push(transport = Transport.LONG_POLLING)
@CDIUI("")
public class MyVaadinUI extends UI {

    @Inject
    private Logger logger;

    @Inject
    private UserProfileHolder userService;

    @Inject
    private CDINavigator nav;

    // Note: Here we do not inject CDIViewProvider, since it is actually not
    // mandatory
    // if you are ok with default view provider, it is used automatically

    private CssLayout contentArea;
    private HorizontalLayout actions;
    private VerticalLayout rootLayout;

    private Button adminButton;

    private boolean printMode = false;

    private Button label;

    @Override
    protected void init(VaadinRequest request) {
        logger.info("UI Init: " + VaadinServlet.getCurrent().getServletContext()
                .getContextPath());
        createRootLayout();
        createActions();
        createContentArea();
        setSizeFull();
        nav.init(this, contentArea);
        // Process possible query parameters and check they contain
        // the one we are interested
        Map<String, String[]> params = request.getParameterMap();
        if (params != null) {
            if (params.get("print") != null) {
                printMode = true;
            }
        }
        String uriFragment = Page.getCurrent().getUriFragment();
        if (uriFragment != null && uriFragment.equals("!" + MainView.VIEW)) {
            nav.navigateTo(MainView.VIEW);
        } else {
            nav.navigateTo(LoginView.VIEW);
        }
    }

    @PostConstruct
    public void setupNavigator() {
        logger.info("UI PostConstruct");
    }

    public boolean getPrintMode() {
        return printMode;
    }

    // Todo: Refactor these to navigation service
    private void navigateToMain(@Observes LoginEvent event) {
        Notification.show("Login succesful! Welcome " + event.getUser());
        adminButton.setEnabled(userService.isAdmin());
        logger.info("Rerouting to main view");
        nav.navigateTo(MainView.VIEW);
    }

    private void navigateToMain(@Observes AlreadyLoggedInEvent event) {
        adminButton.setEnabled(userService.isAdmin());
        logger.info("Rerouting to main view");
        nav.navigateTo(MainView.VIEW);
    }

    private void navigateToLogin(@Observes NotLoggedInEvent event) {
        Notification.show("Please login first");
        logger.info("Rerouting to login page");
        nav.navigateTo(LoginView.VIEW);
    }

    private void createRootLayout() {
        rootLayout = new VerticalLayout();
        rootLayout.setSizeFull();
        setContent(rootLayout);
    }

    private void createActions() {
        actions = new HorizontalLayout();
        actions.setWidth("100%");
        label = new Button("CDI Demo");
        label.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        label.addClickListener(event -> {
            nav.navigateTo(MainView.VIEW + "/hello=world");
        });
        adminButton = new Button("admin", event -> {
            nav.navigateTo(AdminView.VIEW);
        });
        adminButton.setEnabled(userService.getUser() != null
                && userService.getUser().isAdmin());
        Button logoutButton = new Button("logout", event -> {
            // Stop Push in order to avoid stupid exception
            UI.getCurrent().getPushConfiguration()
                    .setPushMode(PushMode.DISABLED);
            userService.logout();
            getPage().setLocation("");
        });
        actions.addComponents(label, adminButton, logoutButton);
        actions.setExpandRatio(label, 1);
        actions.setComponentAlignment(adminButton, Alignment.TOP_RIGHT);
        actions.setComponentAlignment(logoutButton, Alignment.TOP_RIGHT);
        rootLayout.addComponent(actions);
    }

    private void createContentArea() {
        contentArea = new CssLayout();
        contentArea.setSizeFull();
        rootLayout.addComponent(contentArea);
        rootLayout.setExpandRatio(contentArea, 1.0f);
    }

    @WebServlet(value = { "/ui/*", "/VAADIN/*" }, asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class, closeIdleSessions = true, heartbeatInterval = 120)
    // With CDI one needs to extend VaadinCDIServlet instead of VaadinServlet
    public static class MyServlet extends VaadinCDIServlet {
        @Override
        protected void servletInitialized() throws ServletException {
            super.servletInitialized();

            getService().addSessionInitListener(event -> {
                event.getSession().getSession().setMaxInactiveInterval(600);
            });

            getService()
                    .setSystemMessagesProvider(new SystemMessagesProvider() {
                        @Override
                        public SystemMessages getSystemMessages(
                                SystemMessagesInfo systemMessagesInfo) {
                            CustomizedSystemMessages messages = new CustomizedSystemMessages();
                            messages.setSessionExpiredNotificationEnabled(
                                    false);
                            messages.setSessionExpiredURL(null);
                            messages.setCommunicationErrorNotificationEnabled(
                                    false);
                            messages.setCommunicationErrorURL(null);
                            return messages;
                        }
                    });
        }

    }
}
