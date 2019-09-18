package org.vaadin.cdidemo.views.main;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.vaadin.cdidemo.MyVaadinUI;
import org.vaadin.cdidemo.VersionLabel;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

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

	private HorizontalLayout container = new HorizontalLayout();

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
		// View parameters can be added directly to URI fragments
		opener.setUriFragment("!"+MainView.VIEW+"/hello=world");
		// Query parameters are given with setParameter of BrowserWindowOpener
		opener.setParameter("print", "true");
		opener.extend(button);		
				
		addComponents(label, button, container, busLabel);
		setComponentAlignment(label, Alignment.MIDDLE_CENTER);
		setComponentAlignment(button, Alignment.MIDDLE_CENTER);	
		setComponentAlignment(container, Alignment.MIDDLE_CENTER);
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
		// View parameters can be accessed via ViewChangeEvent
		Map<String, String> params = event.getParameterMap();
		if (params != null) {
			for (String key : params.keySet()) {
				Label label = new Label(key+" = "+params.get(key));
				container.addComponent(label);
				container.setComponentAlignment(label, Alignment.MIDDLE_LEFT);
			}
		}
		// Query parameters are handled in UI
		MyVaadinUI ui = (MyVaadinUI) UI.getCurrent();
		if (ui.getPrintMode()) {
			Label label = new Label("Printing");
			container.addComponent(label);
			container.setComponentAlignment(label, Alignment.MIDDLE_RIGHT);			
		}
		// According to MVP pattern we should not call business logic
		// in view directly, we delegate to presenter
		presenter.requestUpdateBusLabel();
	}
}
