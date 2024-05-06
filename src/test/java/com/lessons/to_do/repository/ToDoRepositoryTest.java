package com.lessons.to_do.repository;


import com.lessons.to_do.models.ToDo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Date;
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
public class ToDoRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14-alpine");

    @Autowired
    ToDoRepository repository;

    @BeforeEach
    void setUp() {
    }

    @AfterAll
    public static void  finish(){
        postgres.close();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void getAllToDo() {
        assertThat(repository.getAllNote()).hasSizeBetween(3,5);
    }

    @Test
    void getOneNote(){
        ToDo toDo = ToDo.builder()
                .id(1L)
                .title("Title")
                .description("Description first note")
                .content("Content second note")
                .dateOfCreated(Date.valueOf("2024-04-22"))
                .dateOfCompleted(Date.valueOf("2024-04-23"))
                .build();
        assertThat(repository.getNoteById(1L)).contains(toDo);
    }

    @Test
    void getOneNoteNoPresent(){
        assertThat(repository.getNoteById(500L)).isEmpty();
    }

    @Test
    void deleteOneNote(){
        assertThat(repository.deleteNoteById(3L)).isNotEqualTo(null);
    }

    @Test
    void deleteNone(){
        assertThat(repository.deleteNoteById(500L)).isEqualTo(null);
    }

    @Test
    void updateNone(){
        ToDo toDo = ToDo.builder()
                .id(500L)
                .title("Title second new")
                .description("Description new second note")
                .content("Content new second note")
                .dateOfCreated(Date.valueOf("2024-04-25"))
                .dateOfCompleted(Date.valueOf("2024-04-25"))
                .build();

        assertThat(repository.updateNote(toDo)).isEqualTo(toDo);

    }

    @Test
    void updateOneToDo(){
        ToDo toDo = ToDo.builder()
                .id(2L)
                .title("Title second new")
                .description("Description new second note")
                .content("Content new second note")
                .dateOfCreated(Date.valueOf("2024-04-25"))
                .dateOfCompleted(Date.valueOf("2024-04-25"))
                .build();

        Comparator<ToDo> comparator = Comparator
                .comparing(ToDo::getId)
                .thenComparing(ToDo::getContent)
                .thenComparing(ToDo::getDescription)
                .thenComparing(ToDo::getTitle);

        assertThat(repository.updateNote(toDo)).usingComparator(comparator).isEqualTo(toDo);

    }


    @Test
    void insertNewToDo(){
        ToDo toDo = ToDo.builder()
                .id(5L)
                .title("Title fifth new")
                .description("Description fifth note")
                .content("Content fifth note")
                .dateOfCreated(Date.valueOf("2024-04-25"))
                .dateOfCompleted(Date.valueOf("2024-04-25"))
                .build();

        Comparator<ToDo> comparator = Comparator
                .comparing(ToDo::getContent)
                .thenComparing(ToDo::getDescription)
                .thenComparing(ToDo::getTitle);

        assertThat(repository.insertNote(toDo)).usingComparator(comparator).isEqualTo(toDo);

    }

}
