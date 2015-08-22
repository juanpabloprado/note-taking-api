package com.juanpabloprado.notes.representations;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Juan on 8/21/2015.
 */
public class User {

    public static final String TAG = User.class.getSimpleName();

    @NotBlank
    @Length(min = 2, max = 50)
    private final String username;

    @NotBlank
    @Length(min = 2, max = 50)
    private final String password;

    @NotBlank
    @Length(min = 2, max = 89)
    private final String email;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User() {
        username = null;
        password = null;
        email = null;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
