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

    public static final String TAG = Note.class.getSimpleName();

    private final int id;

    @NotBlank
    @Length(min = 2, max = 50)
    private final String title;

    private final String content;

    public Note(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Note() {
        id = 0;
        title = null;
        content = null;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
