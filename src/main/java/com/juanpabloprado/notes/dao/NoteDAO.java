package com.juanpabloprado.notes.dao;

import com.google.common.base.Optional;
import com.hubspot.rosetta.jdbi.BindWithRosetta;
import com.juanpabloprado.notes.representations.Note;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.SingleValueResult;

import java.util.List;

/**
 * Created by Juan on 8/21/2015.
 */
public interface NoteDAO {
    @GetGeneratedKeys
    @SqlUpdate("insert into note (title, content, username) values ( :title, :content, :username)")
    int createNote(@BindWithRosetta Note note, @Bind("username") String username);

    @SqlUpdate("update note set title = :title, content = :content where id = :id and username = :username")
    void updateNote(@BindWithRosetta Note note, @Bind("username") String username);

    @SqlUpdate("delete from note where id = :id")
    void deleteNote(@Bind("id") int id);

    @SqlQuery("select * from note where username = :username")
    List<Note> getNotes(@Bind("username") String username);

    @SingleValueResult
    @SqlQuery("select * from note where id = :id")
    Optional<Note> getNote(@Bind("id") int id);
}
