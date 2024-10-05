package org.vaadin.cdidemo.data;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

@SessionScoped
public class SessionBeanImpl implements Serializable, SessionBean {

    private String text = "Unset";

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
