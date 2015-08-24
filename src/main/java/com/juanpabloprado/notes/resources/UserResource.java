package com.juanpabloprado.notes.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hubspot.jackson.jaxrs.PropertyFiltering;
import com.juanpabloprado.notes.dao.UserDAO;
import com.juanpabloprado.notes.representations.User;
import com.juanpabloprado.notes.util.LoggerJsonObject;
import io.dropwizard.auth.Auth;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by Juan on 8/22/2015.
 */
@Path("/v1/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource extends GenericResource<User> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    private final UserDAO userDAO;

    public UserResource(DBI jdbi) {
        userDAO = jdbi.onDemand(UserDAO.class);
    }

    @GET
    public Response getUsers(){
        List<User> users = userDAO.getUsers();
        return Response.ok(users).build();
    }

    @GET
    @Path("/{username}")
    @PropertyFiltering
    public Response getUser(@PathParam("username") String username) {
        User user = findSafely(userDAO.getUser(username), User.TAG);
        return Response.ok(user).build();
    }



    @POST
    public Response createUser(@Valid User user) throws JsonProcessingException, URISyntaxException {
        LoggerJsonObject.logObject(user, LOGGER);
        userDAO.createUser(user);
        return Response.created(new URI(user.getUsername())).entity(user).build();
    }

    @DELETE
    @Path("/{username}")
    public Response deleteUser(@PathParam("username") String username, @Auth User authenticatedUser) {
        userDAO.deleteUser(username);
        return Response.noContent().build();
    }

    @POST
    @Path("/{username}")
    public Response updateUser(@PathParam("username") String username, @Valid User user, @Auth User authenticatedUser) throws JsonProcessingException {
        LoggerJsonObject.logObject(user, LOGGER);
        userDAO.updateUser(username, user);
        return Response.ok(user).build();
    }

}
