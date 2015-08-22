package com.juanpabloprado.notes.resources;

import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Juan on 8/21/2015.
 */
@Path("/v1/notes")
@Produces(MediaType.APPLICATION_JSON)
public class NoteResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoteResource.class);

    public NoteResource(DBI jdbi) {
    }

    @GET
    public Response getNotes(){
        return Response.ok().build();
    }
}
