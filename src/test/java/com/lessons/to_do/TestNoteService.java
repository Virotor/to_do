package com.lessons.to_do;


import com.lessons.to_do.models.Note;
import com.lessons.to_do.repository.NoteRepository;
import com.lessons.to_do.service.NoteService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class TestNoteService {

    @Mock
    private NoteRepository mockedNoteRepository;


    @Test
    public void testDelete(){
        when(mockedNoteRepository.deleteNoteById(1L)).thenReturn(1);
        NoteService noteService = new NoteService(mockedNoteRepository);
        assertEquals(noteService.deleteNoteById(1L), "Запись удалена");
    }

    @Test
    public void testNotDelete(){
        when(mockedNoteRepository.deleteNoteById(2L)).thenReturn(0);
        NoteService noteService = new NoteService(mockedNoteRepository);
        assertEquals(noteService.deleteNoteById(2L), "Не удалось удалить запись");
    }

    @Test
    public void testGetAll(){
        when(mockedNoteRepository.getAllNote()).thenReturn(Stream.generate(Note::new).limit(10).collect(Collectors.toList()));
        NoteService noteService = new NoteService(mockedNoteRepository);
        assertEquals(noteService.getAllNotes().size(), 10);
    }


    @Test
    public void testGetById(){
        when(mockedNoteRepository.getNoteById(1L)).thenReturn(Optional.ofNullable(Note.builder().id(1L).build()));
        NoteService noteService = new NoteService(mockedNoteRepository);
        assertTrue(noteService.getNoteById(1L).isPresent());
    }
}
