package com.juanpabloprado.notes.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.juanpabloprado.notes.dao.TokenDAO;
import com.juanpabloprado.notes.dao.UserDAO;
import com.juanpabloprado.notes.representations.Token;
import com.juanpabloprado.notes.representations.User;
import com.juanpabloprado.notes.util.LoggerJsonObject;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * Created by Juan on 8/22/2015.
 */
@Path("/tokens")
@Produces(MediaType.APPLICATION_JSON)
public class TokenResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    private final UserDAO userDAO;
    private final TokenDAO tokenDAO;

    public TokenResource(DBI jdbi) {
        userDAO = jdbi.onDemand(UserDAO.class);
        tokenDAO = jdbi.onDemand(TokenDAO.class);
    }

    @POST
    @Transactional
    public Response createToken(@Valid User user) throws JsonProcessingException, URISyntaxException {
        LoggerJsonObject.logObject(user, LOGGER);
        boolean validUser = (userDAO.getUser(user) == 1);
         if (!validUser) {
             throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        Token token = new Token(UUID.randomUUID().toString(), user.getUsername());
        tokenDAO.createToken(token);

        return Response.created(new URI(token.getUsername())).entity(token).build();
    }
}
