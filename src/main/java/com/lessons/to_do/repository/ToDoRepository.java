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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ToDoRepository {

    private final DatabaseConnector dataBaseConnector;



    @Cacheable(value = "noteAll", key = "#root.targetClass")
    public List<ToDo> getAllNote() {
        log.info("Получение всех пользователей");
        return dataBaseConnector.getConnect().query(
                "SELECT * FROM note",
                (rs, rowNum) -> ToDo.builder()
                        .id(rs.getLong("id"))
                        .dateOfCompleted(rs.getDate("date_completed"))
                        .content(rs.getString("content"))
                        .description(rs.getString("description"))
                        .dateOfCreated(rs.getDate("date_create"))
                        .title(rs.getString("title"))
                        .build()
        );
    }

    @Cacheable(value = "note", key = "#id")
    public Optional<ToDo> getNoteById(@NotNull Long id) {
        log.info("Получение записи с id " + id);
        return dataBaseConnector.getConnect().query(
                "SELECT * FROM note " +
                        "where id = :id ",
                Map.of("id", id),
                (rs, rowNum) -> ToDo.builder()
                        .id(rs.getLong("id"))
                        .dateOfCompleted(rs.getDate("date_completed"))
                        .content(rs.getString("content"))
                        .description(rs.getString("description"))
                        .dateOfCreated(rs.getDate("date_create"))
                        .title(rs.getString("title"))
                        .build()
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
                        delete from note as n where n.id = :id
                        """
                , Map.of("id", id)
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
    public int updateNote(@NotNull ToDo toDoToUpdate) {
        log.info("Обновление записи " + toDoToUpdate);
        return dataBaseConnector.getConnect().update(
                """                            
                        update note
                        Set title = :title, description = :desc , content = :cont , date_completed = :date
                        where id = :id
                        """,
                Map.of(
                        "title", toDoToUpdate.getTitle(),
                        "desc", toDoToUpdate.getDescription(),
                        "cont", toDoToUpdate.getContent(),
                        "date", toDoToUpdate.getDateOfCompleted(),
                        "id", toDoToUpdate.getId()
                )
        );
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
                        VALUES ( :title , :desc , :cont, now(), :date)
                        """,
                        Map.of(
                                "title", toDoToInsert.getTitle(),
                                "desc", toDoToInsert.getDescription(),
                                "cont", toDoToInsert.getContent(),
                                "date", toDoToInsert.getDateOfCompleted()
                        )
        );
    }
}
