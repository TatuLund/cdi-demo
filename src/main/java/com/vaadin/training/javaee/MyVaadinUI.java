package com.vaadin.training.javaee;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDINavigator;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Theme("valo")
@SuppressWarnings("serial")
@PreserveOnRefresh
@CDIUI("")
public class MyVaadinUI extends UI {

	@Inject
	private Logger logger;
	
	@Inject
	private UserService userService; 

	@Inject
	private CDINavigator nav;
	
	// Note: Here we do not inject CDIViewProvider, since it is actually not mandatory
	// if you are ok with default view provider, it is used automatically
	
	private CssLayout contentArea;
	private HorizontalLayout actions;
	private VerticalLayout rootLayout;

	private Button adminButton;
	
	@Override
	protected void init(VaadinRequest request) {
		logger.info("UI Init: "+VaadinServlet.getCurrent().getServletContext().getContextPath());
		createRootLayout();
		createActions();
		createContentArea();
		setSizeFull();
		nav.init(this, contentArea);		
		String uriFragment = Page.getCurrent().getUriFragment();
		if (uriFragment != null && uriFragment.equals("!"+MainView.VIEW)) {
			nav.navigateTo(MainView.VIEW);			
		} else {
			nav.navigateTo(LoginView.VIEW);			
		}
	}

	@PostConstruct
	public void setupNavigator() {
		logger.info("UI PostConstruct");
	}	
	
	private void navigateToMain(@Observes LoginEvent event) {
		Notification.show("Login succesful! Welcome "+event.getUser());
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
		Button label = new Button("CDI Demo");
		label.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		label.addClickListener(event -> {
			nav.navigateTo(MainView.VIEW);;
		});
		adminButton = new Button("admin", event -> { 
			nav.navigateTo(AdminView.VIEW);
		});
		adminButton.setEnabled(false);
		Button logoutButton = new Button("logout", event -> { 
			userService.logout();
			getPage().setLocation("");
		});
		actions.addComponents(label,adminButton,logoutButton);
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
		
}
