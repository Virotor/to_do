package com.lessons.to_do;


import com.lessons.to_do.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Utils implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ToDoApplication.class);

    private final ToDoRepository toDoRepository;

    @Override
    public void run(String... args) {
        toDoRepository.findAll().forEach(e ->
                log.info(e.toString())
        );
    }
}
