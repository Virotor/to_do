package com.lessons.to_do.repository;


import com.lessons.to_do.DAO.DataBaseController;
import com.lessons.to_do.models.Note;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NoteRepository {

    private final DataBaseController dataBaseConnector;

    private static class NoteRowMapper implements RowMapper<Note> {

        @Override
        public Note mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Note.builder()
                    .id(rs.getLong("id"))
                    .dateOfCompleted(rs.getDate("date_completed"))
                    .content(rs.getString("content"))
                    .description(rs.getString("description"))
                    .dateOfCreated(rs.getDate("date_create"))
                    .title(rs.getString("title"))
                    .build();
        }
    }

    private static class NoteInserter implements ParameterizedPreparedStatementSetter<Note> {

        @Override
        public void setValues(PreparedStatement ps, Note argument) throws SQLException {
            ps.setString(1, argument.getTitle());
            ps.setString(2, argument.getDescription());
            ps.setString(3, argument.getContent());
            ps.setDate(4, new java.sql.Date(argument.getDateOfCreated().getTime()));
            ps.setDate(5,
                    argument.getDateOfCompleted() == null ?
                            null
                            :
                            new java.sql.Date(argument.getDateOfCompleted().getTime()));
        }
    }

    public List<Note> getAllNote() {
        return dataBaseConnector.get(
                "SELECT * FROM note",
                new NoteRowMapper()
        );
    }

    public Optional<Note> getNoteById(@NotNull Long id) {
        return dataBaseConnector.get(
                "SELECT * FROM note " +
                        "where id = ? ",
                new NoteRowMapper(),
                id
        ).stream().findAny();
    }

    public int deleteNoteById(@NotNull Long id){
        return dataBaseConnector.update(
                """
                        delete from note as n where n.id = ?
                        """
                ,id
        );
    }

    public Note updateNote(@NotNull Note noteToUpdate) {
        return dataBaseConnector.update(
                """                            
                        update note
                        Set title = ?, description = ? , content = ? , date_completed = ?
                        where id = ?
                        """,
                noteToUpdate.getTitle(),
                noteToUpdate.getDescription(),
                noteToUpdate.getContent(),
                noteToUpdate.getDateOfCompleted(),
                noteToUpdate.getId()
        ) == 1 ? noteToUpdate : null;
    }

    public int insertNote(@NotNull Note noteToInsert){
        return  dataBaseConnector.insert(
                """
                        INSERT INTO note(title, description, content, date_create, date_completed)
                        VALUES (?,?,?,?,?)
                        """,
                        Collections.singletonList(noteToInsert),
                        new NoteInserter()
        );
    }
}
