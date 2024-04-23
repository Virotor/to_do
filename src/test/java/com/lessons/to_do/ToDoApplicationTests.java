package com.lessons.to_do;

import com.lessons.to_do.models.Note;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ToDoApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
            .withPassword("inmemory")
            .withUsername("inmemory");
    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }
    @Test
    void testThatSizeIsFourths() {
        var res = this.restTemplate.getForEntity("http://localhost:" + port + "/note/all",
                List.class);
        assertEquals(Objects.requireNonNull(res.getBody()).size(), 3);
    }

    @Test
    void getOneNote() {
        var res = this.restTemplate.getForEntity("http://localhost:" + port + "/note/get/1",
                Note.class);
        assertEquals(Objects.requireNonNull(res.getBody()).getId(), 1);
    }


    @Test
    void deleteOne() {
        this.restTemplate.delete("http://localhost:" + port + "/note/delete/2");
        var res = this.restTemplate.getForEntity("http://localhost:" + port + "/note/get/2",
                Note.class);
        assertEquals(res.getBody(), null);
    }


}
