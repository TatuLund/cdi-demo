package org.vaadin.cdidemo.rest;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.slf4j.Logger;
import org.vaadin.cdidemo.eventbus.EventBus;

@Path("")
@RequestScoped
public class DemoEndpoint {

    @Inject
    private EventBus eventBus;

    @Inject
    private Logger logger;

    @GET
    @Path("/hello/{parameter}")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@PathParam("parameter") String parameter) {
        parameter = Jsoup.clean(parameter, Safelist.none());
        logger.info("REST GET hello: "+parameter);
        RestMessageEvent event = new RestMessageEvent(parameter);
        eventBus.post(event);
        return "Hello "+parameter;
    }    
}
