package com.juanpabloprado.notes.auth;

import com.google.common.base.Optional;
import com.juanpabloprado.notes.dao.TokenDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import org.skife.jdbi.v2.DBI;

/**
 * Created by Juan on 8/22/2015.
 */
public class NotesAuthenticator implements Authenticator<String, Boolean> {

    private final TokenDAO tokenDAO;

    public NotesAuthenticator(DBI jdbi) {
        tokenDAO = jdbi.onDemand(TokenDAO.class);
    }

    public Optional<Boolean> authenticate(String accessToken) throws AuthenticationException {
        boolean validToken = (tokenDAO.getToken(accessToken) == 1);
        if (validToken) {
            return Optional.of(true);
        }
        return Optional.absent();
    }
}
