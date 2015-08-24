package com.juanpabloprado.notes.auth;

import com.google.common.base.Optional;
import com.juanpabloprado.notes.dao.TokenDAO;
import com.juanpabloprado.notes.dao.UserDAO;
import com.juanpabloprado.notes.representations.Token;
import com.juanpabloprado.notes.representations.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import org.skife.jdbi.v2.DBI;

/**
 * Created by Juan on 8/22/2015.
 */
public class NotesAuthenticator implements Authenticator<String, User> {

    private final TokenDAO tokenDAO;
    private final UserDAO userDAO;

    public NotesAuthenticator(DBI jdbi) {
        tokenDAO = jdbi.onDemand(TokenDAO.class);
        userDAO = jdbi.onDemand(UserDAO.class);
    }

    public Optional<User> authenticate(String accessToken) throws AuthenticationException {
        Optional<Token> validToken = tokenDAO.getToken(accessToken);
        if (validToken.isPresent()) {
            return userDAO.getUser(validToken.get().getUsername());
        }
        return Optional.absent();
    }
}
