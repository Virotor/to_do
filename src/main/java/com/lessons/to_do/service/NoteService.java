package com.lessons.to_do.service;


import com.lessons.to_do.DTO.ToDoRequest;
import com.lessons.to_do.DTO.ToDoUpdateRequest;
import com.lessons.to_do.models.ToDo;
import com.lessons.to_do.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final ToDoRepository toDoRepository;

    public String addNewNote(ToDoRequest toDoRequest) {
        int result = toDoRepository.insertNote(
                ToDo.builder()
                        .title(toDoRequest.getTitle())
                        .content(toDoRequest.getContent())
                        .description(toDoRequest.getDescription())
                        .dateOfCreated(new Date())
                        .build()
        );
        if (result != 1) {
            return  "Не удалось создать запись";
        }
        return "Задача добавлена";
    }

    public List<ToDo> getAllNotes() {
        return toDoRepository.getAllNote();
    }

    public Optional<ToDo> getNoteById(Long id) {
        return toDoRepository.getNoteById(id);
    }

    public String deleteNoteById(Long id) {
        int result = toDoRepository.deleteNoteById(id);
        if (result != 1) {
            return "Не удалось удалить запись";
        }
        return "Запись удалена";
    }

    public ToDo updateNote(ToDoUpdateRequest toDoUpdateRequest) {
        var result = toDoRepository.updateNote(
                ToDo.builder()
                        .title(toDoUpdateRequest.getToDoRequest().getTitle())
                        .content(toDoUpdateRequest.getToDoRequest().getContent())
                        .description(toDoUpdateRequest.getToDoRequest().getDescription())
                        .id(toDoUpdateRequest.getId())
                        .dateOfCompleted(toDoUpdateRequest.getDateOfCompleted())
                        .build()
        );
        if (result == null) {
            throw new RuntimeException("Не удалось изменить запись");
        }
        return result;
    }
}
