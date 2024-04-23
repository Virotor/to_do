package com.lessons.to_do.controller;


import com.lessons.to_do.DTO.ToDoRequest;
import com.lessons.to_do.DTO.ToDoUpdateRequest;
import com.lessons.to_do.service.NoteService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/note")
@RequiredArgsConstructor
@Tag(name="Методы для работы с запиями в TODO", description =  "API для работы с записями в TODO листах, позволяет их создавать, удалять, редактировать")
public class NoteController {

    private  final NoteService noteService;


    @PostMapping("/add")
    @Schema(description = "Метод для добавления новой записи в бд")
    public ResponseEntity<?> addNewNote(@RequestBody ToDoRequest toDoRequest){
        return  noteService.addNewNote(toDoRequest);
    }

    @GetMapping("/all")
    @Schema(description = "Метод для получения всех записей")
    public ResponseEntity<?> getAllNote(){
        return noteService.getAllNotes();
    }

    @GetMapping("/get{id}")
    @Schema(description = "Метод для получения записи с конкретным id")
    public ResponseEntity<?> getNoteById(@PathVariable Long id){
        return noteService.getNoteById(id);
    }

    @DeleteMapping("/delete{id}")
    @Schema(description = "Метод для удаления записи с конкретным id")
    public ResponseEntity<?> deleteNoteById(@PathVariable Long id){
        return  noteService.deleteNoteById(id);
    }

    @PostMapping("/update")
    @Schema(description = "Метод для изменения записи")
    public ResponseEntity<?> deleteNoteById(@RequestBody ToDoUpdateRequest toDoUpdateRequest){
        return  noteService.updateNote(toDoUpdateRequest);
    }
}
