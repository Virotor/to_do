package com.lessons.to_do;


import com.lessons.to_do.models.Note;
import com.lessons.to_do.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class Utils implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ToDoApplication.class);

    private final NoteRepository noteRepository;

    @Override
    public void run(String... args) throws Exception {
//        noteRepository.getAllNote().forEach(e ->
//                {
//                    log.info(e.toString());
//                    noteRepository.updateNote(Note.builder()
//                            .id(e.getId())
//                            .title(e.getTitle() + "New title")
//                            .content(e.getContent())
//                            .dateOfCompleted(new Date())
//                            .dateOfCreated(e.getDateOfCreated())
//                            .description(e.getDescription())
//                            .build());
//                }
//        );
        noteRepository.getAllNote().forEach(e ->
                {
                    log.info(e.toString());
                }
        );
    }
}
