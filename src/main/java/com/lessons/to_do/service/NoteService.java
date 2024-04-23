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

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public ResponseEntity<?> addNewNote(ToDoRequest toDoRequest) {
        int result = noteRepository.insertNote(
                Note.builder()
                        .title(toDoRequest.getTitle())
                        .content(toDoRequest.getContent())
                        .description(toDoRequest.getDescription())
                        .dateOfCreated(new Date())
                        .build()
        );
        if (result != 1) {
            return ResponseEntity.badRequest().body("Не удалось создать запись");
        }
        return ResponseEntity.ok("Задача добавлена");
    }

    public ResponseEntity<?> getAllNotes() {
        return ResponseEntity.ok(noteRepository.getAllNote());
    }

    public ResponseEntity<?> getNoteById(Long id) {
        return ResponseEntity.ok(noteRepository.getNoteById(id));
    }

    public ResponseEntity<?> deleteNoteById(Long id) {
        int result = noteRepository.deleteNoteById(id);
        if (result != 1) {
            return ResponseEntity.badRequest().body("Не удалось удалить запись");
        }
        return ResponseEntity.ok("Запись удалена");
    }

    public ResponseEntity<?> updateNote(ToDoUpdateRequest toDoUpdateRequest) {
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
            return ResponseEntity.badRequest().body("Не удалось изменить запись");
        }
        return ResponseEntity.ok(result);
    }
}
