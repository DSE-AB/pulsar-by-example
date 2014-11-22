package se.dse.pulsar.devtools.wiki.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/")
public interface Wiki {
    @GET
    @Path("index")
    @Produces("text/html")
    String index();

    @GET
    @Path("index/{pagePath: .*}")
    @Produces("text/html")
    String page(@PathParam("pagePath") String i_pagePath);

}
