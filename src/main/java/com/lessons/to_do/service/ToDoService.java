package com.lessons.to_do.service;


import com.lessons.to_do.DTO.ToDoRequest;
import com.lessons.to_do.DTO.ToDoUpdateRequest;
import com.lessons.to_do.models.ToDo;
import com.lessons.to_do.repository.ToDoRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepository toDoRepository;

    public int addNewNote(@NonNull ToDoRequest toDoRequest) {
        return  toDoRepository.insertNote(
                ToDo.builder()
                        .title(toDoRequest.getTitle())
                        .content(toDoRequest.getContent())
                        .description(toDoRequest.getDescription())
                        .build()
        );
    }

    public List<ToDo> getAllNotes() {
        return toDoRepository.getAllNote();
    }

    public Optional<ToDo> getNoteById(@NonNull Long id) {
        return toDoRepository.getNoteById(id);
    }

    public Integer deleteNoteById(@NonNull  Long id) {
       return toDoRepository.deleteNoteById(id);
    }

    public int updateNote(@NonNull ToDoUpdateRequest toDoUpdateRequest) {
        return toDoRepository.updateNote(
                ToDo.builder()
                        .title(toDoUpdateRequest.getToDoRequest().getTitle())
                        .content(toDoUpdateRequest.getToDoRequest().getContent())
                        .description(toDoUpdateRequest.getToDoRequest().getDescription())
                        .id(toDoUpdateRequest.getId())
                        .dateOfCompleted(toDoUpdateRequest.getDateOfCompleted())
                        .build()
        );
    }
}
