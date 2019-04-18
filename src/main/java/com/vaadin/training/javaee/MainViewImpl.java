package com.vaadin.training.javaee;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Solution to the bonus task
 *
 * @author Vaadin Ltd
 *
 */
@SuppressWarnings("serial")
@CDIView(MainView.VIEW)
public class MainViewImpl extends VerticalLayout implements MainView, View {

	@Inject
	private MainPresenter presenter;
	
	@Inject
	private VersionLabel versionLabel;
	
	@Inject
	private Logger logger;
	
	private Label busLabel = new Label("no data");

	public MainViewImpl() {
		setSizeFull();

		Label label = new Label("main view");
		label.addStyleName(ValoTheme.LABEL_H1);
		label.setSizeUndefined();

		busLabel.addStyleName(ValoTheme.LABEL_BOLD);
		busLabel.setSizeUndefined();

		// Demoing session scoped user management, once logged in 
		// navigation to main possible in in new UI within same session
		Button button = new Button("Open");
		button.setDescription("Click this button to open new main view in an another browser tab");
		BrowserWindowOpener opener = new BrowserWindowOpener(VaadinServlet.getCurrent().getServletContext().getContextPath());
		opener.setUriFragment("!"+MainView.VIEW);
		opener.extend(button);		
		
		addComponents(label, button, busLabel);
		setComponentAlignment(label, Alignment.MIDDLE_CENTER);
		setComponentAlignment(button, Alignment.MIDDLE_CENTER);		
		setComponentAlignment(busLabel, Alignment.MIDDLE_CENTER);		
	}

	@PostConstruct
	public void init() {
		presenter.setView(this);
		addComponents(versionLabel);
		setComponentAlignment(versionLabel, Alignment.BOTTOM_RIGHT);		
	}
	
	public void updateBusLabel(String businessData) {
		busLabel.setValue(businessData);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		presenter.handleLoggedIn();
		
		// According to MVP pattern we should not call business logic
		// in view directly, we delegate to presenter
		presenter.requestUpdateBusLabel();
	}
}
