package com.lessons.to_do.service;


import com.lessons.to_do.DTO.ToDoRequest;
import com.lessons.to_do.DTO.ToDoUpdateRequest;
import com.lessons.to_do.models.Note;
import com.lessons.to_do.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public String addNewNote(ToDoRequest toDoRequest) {
        int result = noteRepository.insertNote(
                Note.builder()
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

    public List<Note> getAllNotes() {
        return noteRepository.getAllNote();
    }

    public Optional<Note> getNoteById(Long id) {
        return noteRepository.getNoteById(id);
    }

    public String deleteNoteById(Long id) {
        int result = noteRepository.deleteNoteById(id);
        if (result != 1) {
            return "Не удалось удалить запись";
        }
        return "Запись удалена";
    }

    public Note updateNote(ToDoUpdateRequest toDoUpdateRequest) {
        var result = noteRepository.updateNote(
                Note.builder()
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
