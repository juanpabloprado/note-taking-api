package com.juanpabloprado.notes.representations;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.hubspot.rosetta.annotations.RosettaNaming;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Juan on 8/22/2015.
 */
@RosettaNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class Token {
    @NotBlank
    @Length(min = 2, max = 36)
    private final String accessToken;

    @NotBlank
    @Length(min = 2, max = 20)
    private final String username;

    public Token(String accessToken, String username) {
        this.accessToken = accessToken;
        this.username = username;
    }

    public Token() {
        accessToken = null;
        username = null;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getUsername() {
        return username;
    }
}
