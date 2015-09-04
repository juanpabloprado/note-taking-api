package com.juanpabloprado.notes.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Optional;
import com.hubspot.jackson.jaxrs.PropertyFiltering;
import com.juanpabloprado.notes.dao.NoteDAO;
import com.juanpabloprado.notes.dao.UserDAO;
import com.juanpabloprado.notes.representations.Note;
import com.juanpabloprado.notes.representations.User;
import com.juanpabloprado.notes.util.LoggerJsonObject;
import io.dropwizard.auth.Auth;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by Juan on 8/21/2015.
 */
@Path("/users/{username}/notes")
@Produces(MediaType.APPLICATION_JSON)
public class NoteResource extends GenericResource<Note> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoteResource.class);

    private final NoteDAO noteDAO;
    private final UserDAO userDAO;

    public NoteResource(DBI jdbi) {
        noteDAO = jdbi.onDemand(NoteDAO.class);
        userDAO = jdbi.onDemand(UserDAO.class);
    }

    @GET
    public Response getNotes(@PathParam("username") String username, @Auth User authenticatedUser) {
        if (isValidUser(username, authenticatedUser)) {
            List<Note> notes = noteDAO.getNotes(username);
            return Response.ok(notes).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @GET
    @Path("/{id}")
    @PropertyFiltering
    public Response getNote(@PathParam("username") String username, @PathParam("id") int id, @Auth User authenticatedUser) {
        if (isValidUser(username, authenticatedUser)) {
            Note note = findSafely(noteDAO.getNote(id), Note.TAG);
            return Response.ok(note).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @POST
    @Transactional
    public Response createNote(@PathParam("username") String username, @Valid Note note, @Auth User authenticatedUser) throws JsonProcessingException, URISyntaxException {
        LoggerJsonObject.logObject(note, LOGGER);
        if (isValidUser(username, authenticatedUser)) {
            int newNoteId = noteDAO.createNote(note, username);
            Note newNote = findSafely(noteDAO.getNote(newNoteId), Note.TAG);
            return Response.created(new URI(newNoteId + "")).entity(newNote).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @POST
    @Path("/{id}")
    @Transactional
    public Response updateNote(@PathParam("username") String username, @PathParam("id") int id, @Valid Note note, @Auth User authenticatedUser) throws JsonProcessingException, URISyntaxException {
        LoggerJsonObject.logObject(note, LOGGER);
        if (isValidUser(username, authenticatedUser)) {
            noteDAO.updateNote(new Note(id, note.getTitle(), note.getContent()), username);
            Note newNote = findSafely(noteDAO.getNote(id), Note.TAG);
            return Response.ok(newNote).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteNote(@PathParam("username") String username, @PathParam("id") int id, @Auth User authenticatedUser) {
        if (isValidUser(username, authenticatedUser)) {
            noteDAO.deleteNote(id);
            return Response.noContent().build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    private boolean isValidUser(@PathParam("username") String username, @Auth User authenticatedUser) {
        Optional<User> validUser = userDAO.getUser(username);
        if(validUser.isPresent()) {
            User user = validUser.get();
            if(user.equals(authenticatedUser)) {
                return true;
            }
            else {
                return false;
            }
        }
        throw new WebApplicationException(
                Response.status(Response.Status.NOT_FOUND)
                        .entity(new Error("No user found"))
                        .build());
    }
}
