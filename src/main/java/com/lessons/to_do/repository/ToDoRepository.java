package com.lessons.to_do.repository;


import com.lessons.to_do.DAO.DatabaseConnector;
import com.lessons.to_do.models.ToDo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
@Slf4j
@RequiredArgsConstructor
public class ToDoRepository {

    private final DatabaseConnector dataBaseConnector;

    private static class ToDoRowMapper implements RowMapper<ToDo> {

        @Override
        public ToDo mapRow(ResultSet rs, int rowNum) throws SQLException {
            return ToDo.builder()
                    .id(rs.getLong("id"))
                    .dateOfCompleted(rs.getDate("date_completed"))
                    .content(rs.getString("content"))
                    .description(rs.getString("description"))
                    .dateOfCreated(rs.getDate("date_create"))
                    .title(rs.getString("title"))
                    .build();
        }
    }

    private static class ToDoSetter implements ParameterizedPreparedStatementSetter<ToDo> {

        @Override
        public void setValues(PreparedStatement ps, ToDo argument) throws SQLException {
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

    @Cacheable(value = "noteAll", key = "#root.targetClass")
    public List<ToDo> getAllNote() {
        log.info("Получение всех пользователей");
        return dataBaseConnector.getConnect().query(
                "SELECT * FROM note",
                new ToDoRowMapper()
        );
    }

    @Cacheable(value = "note", key = "#id")
    public Optional<ToDo> getNoteById(@NotNull Long id) {
        log.info("Получение записи с id " + id);
        return dataBaseConnector.getConnect().query(
                "SELECT * FROM note " +
                        "where id = ? ",
                new ToDoRowMapper(),
                id
        ).stream().findAny();
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "note", key = "#id"),
                    @CacheEvict(value = "noteAll", allEntries = true, key = "#root.targetClass")
            }
    )
    public int deleteNoteById(@NotNull Long id) {
        log.info("Удаление записи с id " + id);
        return dataBaseConnector.getConnect().update(
                """
                        delete from note as n where n.id = ?
                        """
                , id
        );
    }

    @Caching(
            put = {
                    @CachePut(value = "note", key="#toDoToUpdate.id"),

            },
            evict = {
                    @CacheEvict(value = "noteAll", key = "#root.targetClass")
            }
    )
    public ToDo updateNote(@NotNull ToDo toDoToUpdate) {
        log.info("Обновление записи " + toDoToUpdate);
        return dataBaseConnector.getConnect().update(
                """                            
                        update note
                        Set title = ?, description = ? , content = ? , date_completed = ?
                        where id = ?
                        """,
                toDoToUpdate.getTitle(),
                toDoToUpdate.getDescription(),
                toDoToUpdate.getContent(),
                toDoToUpdate.getDateOfCompleted(),
                toDoToUpdate.getId()
        ) == 1 ? toDoToUpdate : null;
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "noteAll", key = "#root.targetClass")
            }
    )
    public int insertNote(@NotNull ToDo toDoToInsert) {
        log.info("Добавление записи  " + toDoToInsert);
        return dataBaseConnector.getConnect().update(
                """
                        INSERT INTO note(title, description, content, date_create, date_completed)
                        VALUES (?,?,?,?,?)
                        """,
                Collections.singletonList(toDoToInsert),
                new ToDoSetter()
        );
    }
}
