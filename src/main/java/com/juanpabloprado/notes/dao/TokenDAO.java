package com.juanpabloprado.notes.dao;

import com.hubspot.rosetta.jdbi.BindWithRosetta;
import com.juanpabloprado.notes.representations.Token;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

/**
 * Created by Juan on 8/22/2015.
 */
public interface TokenDAO {
    @SqlQuery("select count(*) from token where access_token = :accessToken")
    int getToken(@Bind("accessToken") String accessToken);

    @SqlUpdate("insert into token (access_token, username) values ( :access_token, :username )")
    void createToken(@BindWithRosetta Token token);
}
