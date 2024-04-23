package com.lessons.to_do.DAO;


import com.lessons.to_do.ToDoApplication;
import com.lessons.to_do.models.Note;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;


@Component
@RequiredArgsConstructor
public class DataBaseController {

    private static final Logger log = LoggerFactory.getLogger(ToDoApplication.class);

    private final JdbcTemplate jdbcTemplate;

    public <T> List<T> get(String sqlQuery, RowMapper<T> rowMapper) {
        return jdbcTemplate.query(sqlQuery, rowMapper);
    }

    public <T> List<T> get(String sqlQuery, RowMapper<T> rowMapper, Object ... args) {
        return jdbcTemplate.query(sqlQuery, rowMapper, args);
    }


    public <T> int update(String sqlQuery, Object... args) {
        return jdbcTemplate.update(
                sqlQuery,
                args
        );
    }

    public <T> int insert(String sqlQuery, Collection<T> source, ParameterizedPreparedStatementSetter<T> setter) {
        return jdbcTemplate.batchUpdate(
                sqlQuery,
                source,
                source.size(),
                setter
        ).length;
    }



    public void run(String... args) throws Exception {
        log.info("Creating tables");

//        jdbcTemplate.execute("CREATE TABLE to_do(" +
//                "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");


        // Split up the array of whole names into an array of first/last names
        List<Note> notes = Arrays.stream(LongStream.iterate(1, (e) -> e += 1)
                        .limit(10).toArray())
                .mapToObj(e -> Note.builder()
                        .title("Note" + e)
                        .id(e)
                        .title("Title" + e)
                        .content("Content" + e)
                        .dateOfCreated(new Date())
                        .description("Description" + e).build()
                )
                .collect(Collectors.toList());

        // Use a Java 8 stream to print out each tuple of the list
        notes.forEach(note -> log.info(String.format("Inserting customer record for %s %s %s %s %s %s", note.getId(), note.getTitle(), note.getContent(), note.getDateOfCompleted(), note.getDescription(), note.getDateOfCreated())));

        // Uses JdbcTemplate's batchUpdate operation to bulk load data
        jdbcTemplate.batchUpdate(
                """
                        INSERT INTO note(title, description, content, date_create, date_completed)
                        VALUES (?,?,?,?,?)
                        """,
                notes, notes.size(), (ps, argument) -> {
                    ps.setString(1, argument.getTitle());
                    ps.setString(2, argument.getDescription());
                    ps.setString(3, argument.getContent());
                    ps.setDate(4, new java.sql.Date(argument.getDateOfCreated().getTime()));
                    ps.setDate(5, null);
                });

        log.info("Querying for customer records where first_name = 'Josh':");
        jdbcTemplate.query(
                        "SELECT id, title, description, content, date_completed, date_create FROM note",
                        (rs, rowNum) -> Note.builder()
                                .id(rs.getLong("id"))
                                .dateOfCompleted(rs.getDate("date_completed"))
                                .content(rs.getString("content"))
                                .description(rs.getString("description"))
                                .dateOfCreated(rs.getDate("date_create"))
                                .title(rs.getString("title"))
                                .build()
                )
                .forEach(customer -> log.info(customer.toString()));
    }
}
