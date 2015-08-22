package com.juanpabloprado.notes;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

/**
 * Created by Juan on 8/21/2015.
 */
public class NotesConfiguration extends Configuration {
    @JsonProperty
    private DataSourceFactory database = new DataSourceFactory();

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }
}
