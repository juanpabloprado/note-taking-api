package com.juanpabloprado.notes.resources;

import com.google.common.base.Optional;

import javax.ws.rs.NotFoundException;

public abstract class GenericResource<E> {

    protected E findSafely(Optional<E> optional, String tag) {
        if (!optional.isPresent()) {
            throw new NotFoundException("No such " + tag + ".");
        }
        return optional.get();
    }

}
