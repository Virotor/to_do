package com.lessons.to_do.api;

import com.lessons.to_do.models.ToDo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ToDoApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Container
    @ServiceConnection
    public static PostgreSQLContainer<?> postgreSQLContainer =  new PostgreSQLContainer<>("postgres")
            .withPassword("inmemory")
            .withUsername("inmemory");

    @BeforeAll
    static public void setContainer(){
        postgreSQLContainer.start();
    }

    @AfterAll
    public static void  closeContainer(){
        postgreSQLContainer.close();
    }

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }
    @Test
    void getAllToDo() {
        var res = this.restTemplate.getForEntity("http://localhost:" + port + "/note/all",
                List.class);
        assertThat(Objects.requireNonNull(res.getBody()).size()).isBetween(1,4);
    }

    @Test
    void getOneNote() {
        var res = this.restTemplate.getForEntity("http://localhost:" + port + "/note/get/1",
                ToDo.class);
        assertEquals(Objects.requireNonNull(res.getBody()).getId(), 1);
    }


    @Test
    void deleteOne() {
        this.restTemplate.delete("http://localhost:" + port + "/note/delete/2");
        var res = this.restTemplate.getForEntity("http://localhost:" + port + "/note/get/2",
                ToDo.class);
        assertEquals(res.getBody(), null);
    }


}
