package com.juanpabloprado.notes.dao;

import com.google.common.base.Optional;
import com.hubspot.rosetta.jdbi.BindWithRosetta;
import com.juanpabloprado.notes.representations.User;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.SingleValueResult;

import java.util.List;

/**
 * Created by Juan on 8/21/2015.
 */
public interface UserDAO {

    @SqlUpdate("insert into user (username, password, email) values ( :username, :password, :email)")
    void createUser(@BindWithRosetta User user);

    @SqlUpdate("update user set username = :username, password = :password, email = :email where username = :oldUsername")
    void updateUser(@Bind("oldUsername") String username, @BindWithRosetta User user);

    @SqlUpdate("delete from user where username = :username")
    void deleteUser(@Bind("username") String username);

    @SqlQuery("select * from user")
    List<User> getUsers();

    @SingleValueResult
    @SqlQuery("select * from user where username = :username")
    Optional<User> getUser(@Bind("username") String username);

    @SqlQuery("select count(*) from user where username = :username and password = :password")
    int getUser(@BindWithRosetta User user);
}
