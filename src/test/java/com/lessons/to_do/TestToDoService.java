package com.lessons.to_do;


import com.lessons.to_do.models.ToDo;
import com.lessons.to_do.repository.ToDoRepository;
import com.lessons.to_do.service.NoteService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class TestToDoService {

    @Mock
    private ToDoRepository mockedToDoRepository;


    @Test
    public void testDelete(){
        when(mockedToDoRepository.deleteNoteById(1L)).thenReturn(1);
        NoteService noteService = new NoteService(mockedToDoRepository);
        assertEquals(noteService.deleteNoteById(1L), "Запись удалена");
    }

    @Test
    public void testNotDelete(){
        when(mockedToDoRepository.deleteNoteById(2L)).thenReturn(0);
        NoteService noteService = new NoteService(mockedToDoRepository);
        assertEquals(noteService.deleteNoteById(2L), "Не удалось удалить запись");
    }

    @Test
    public void testGetAll(){
        when(mockedToDoRepository.getAllNote()).thenReturn(Stream.generate(ToDo::new).limit(10).collect(Collectors.toList()));
        NoteService noteService = new NoteService(mockedToDoRepository);
        assertEquals(noteService.getAllNotes().size(), 10);
    }
    @Test
    public void testGetAllEmpty(){
        when(mockedToDoRepository.getAllNote()).thenReturn(Stream.generate(ToDo::new).limit(0).collect(Collectors.toList()));
        NoteService noteService = new NoteService(mockedToDoRepository);
        assertEquals(noteService.getAllNotes().size(), 0);
    }


    @Test
    public void testGetById(){
        when(mockedToDoRepository.getNoteById(1L)).thenReturn(Optional.ofNullable(ToDo.builder().id(1L).build()));
        NoteService noteService = new NoteService(mockedToDoRepository);
        assertTrue(noteService.getNoteById(1L).isPresent());
    }
    @Test
    public void testGetByIdNotPresent(){
        when(mockedToDoRepository.getNoteById(1L)).thenReturn(Optional.ofNullable(ToDo.builder().id(1L).build()));
        NoteService noteService = new NoteService(mockedToDoRepository);
        assertFalse(noteService.getNoteById(2L).isPresent());
    }
}
