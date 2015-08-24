package com.juanpabloprado.notes.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public abstract class GenericResource<E> {

    protected E findSafely(Optional<E> optional, String tag) {
        if (!optional.isPresent()) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                    .entity(new Error("No such " + tag + "."))
                    .build()
            );
        }
        return optional.get();
    }

    protected class Error
    {
        @JsonProperty
        public final String message;

        public Error(String message) {
            this.message = message;
        }

        public Error() {
            message = null;
        }

        public String getMessage() {
            return message;
        }
    }
}
