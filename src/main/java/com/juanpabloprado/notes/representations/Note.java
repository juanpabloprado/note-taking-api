package com.juanpabloprado.notes.representations;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.hubspot.rosetta.annotations.RosettaNaming;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Juan on 12/18/2014.
 */
@RosettaNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class Note {

    private int id;

    @NotBlank
    @Length(min = 2, max = 50)
    private final String title;

    @NotBlank
    private final String content;

    @NotBlank
    @Length(min = 2, max = 20)
    private final String username;

    public Note(String title, String content, String username) {
        this.title = title;
        this.content = content;
        this.username = username;
    }

    public Note() {
        id = 0;
        title = null;
        content = null;
        username = null;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }
}
