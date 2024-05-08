package com.lessons.to_do.service;


import com.lessons.to_do.DTO.ToDoRequest;
import com.lessons.to_do.DTO.ToDoUpdateRequest;
import com.lessons.to_do.models.ToDo;
import com.lessons.to_do.repository.ToDoRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepository toDoRepository;

    public ToDo addNewNote(@NonNull ToDoRequest toDoRequest) {
        return  toDoRepository.save(
                ToDo.builder()
                        .title(toDoRequest.getTitle())
                        .content(toDoRequest.getContent())
                        .description(toDoRequest.getDescription())
                        .build()
        );
    }

    public List<ToDo> getAllNotes() {
        return toDoRepository.findAll();
    }

    public Optional<ToDo> getNoteById(@NonNull Long id) {
        return toDoRepository.findById(id);
    }

    @Transactional
    public void deleteNoteById(@NonNull  Long id) {
        var toDo = this.toDoRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        toDoRepository.deleteById(id);
    }

    public ToDo updateNote(@NonNull ToDoUpdateRequest toDoUpdateRequest) {
        this.toDoRepository.findById(toDoUpdateRequest.getId()).orElseThrow(IllegalArgumentException::new);
        return toDoRepository.saveAndFlush(
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
