package org.vaadin.cdidemo.rest;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
        logger.info("REST GET hello: "+parameter);
        RestMessageEvent event = new RestMessageEvent(parameter);
        eventBus.post(event);
        return "Hello "+parameter;
    }    
}
