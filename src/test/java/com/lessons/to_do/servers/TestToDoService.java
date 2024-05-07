package com.lessons.to_do.servers;



import com.lessons.to_do.DTO.ToDoRequest;
import com.lessons.to_do.DTO.ToDoUpdateRequest;
import com.lessons.to_do.repository.ToDoRepository;
import com.lessons.to_do.service.ToDoService;
import com.lessons.to_do.models.ToDo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestToDoService {

    @Mock
    private ToDoRepository mockedToDoRepository;

    @InjectMocks
    private ToDoService toDoService;

    @BeforeAll
    public static void initTest(){

    }


    @Test
    public void testAddNewToDo(){
        ToDoRequest toDoRequest = new ToDoRequest();
        toDoRequest.setContent("New ToDo");
        toDoRequest.setDescription("new ToDo");
        toDoRequest.setTitle("ToDo");
        ToDo toDo = ToDo.builder()
                .title(toDoRequest.getTitle())
                .content(toDoRequest.getContent())
                .description(toDoRequest.getDescription())
                .build();
        when(mockedToDoRepository.save(toDo)).thenReturn(toDo);
        assertEquals(toDoService.addNewNote(toDoRequest), toDo);
    }

    @Test
    public void testDelete(){
//        ToDo toDo = new ToDo();
//        when(mockedToDoRepository.deleteById(1L));
//        verify(mockedToDoRepository).deleteNoteById(1L)
//        assertEquals(toDoService.deleteNoteById(1L));
    }


    @Test
    public void testNotDelete(){
//        when(mockedToDoRepository.deleteNoteById(2L)).thenReturn(null);
//        assertEquals(toDoService.deleteNoteById(2L), null);
    }

    @Test
    public void testUpdate(){
        ToDoUpdateRequest toDoUpdateRequest = new ToDoUpdateRequest();
        ToDoRequest toDoRequest = new ToDoRequest();
        toDoRequest.setContent("New ToDo");
        toDoRequest.setDescription("new ToDo");
        toDoRequest.setTitle("ToDo");
        toDoUpdateRequest.setToDoRequest(toDoRequest);
        toDoUpdateRequest.setId(1L);
        toDoUpdateRequest.setDateOfCompleted(new Date());
        ToDo toDo = ToDo.builder()
                .title(toDoRequest.getTitle())
                .content(toDoRequest.getContent())
                .description(toDoRequest.getDescription())
                .dateOfCompleted(toDoUpdateRequest.getDateOfCompleted())
                .id(toDoUpdateRequest.getId())
                .build();

        when(mockedToDoRepository.save(toDo)).thenReturn(toDo);

        assertEquals(toDoService.updateNote(toDoUpdateRequest), toDo);
    }

    @Test
    public void testGetAll(){
        when(mockedToDoRepository.findAll()).thenReturn(Stream.generate(ToDo::new).limit(10).collect(Collectors.toList()));
        assertEquals(toDoService.getAllNotes().size(), 10);
    }

    @Test
    public void testGetById(){
        when(mockedToDoRepository.findById(1L)).thenReturn(Optional.ofNullable(ToDo.builder().id(1L).build()));
        assertTrue(toDoService.getNoteById(1L).isPresent());
    }
    @Test
    public void testGetByIdNotPresent(){
        assertFalse(toDoService.getNoteById(2L).isPresent());
    }


    @Test
    public void testNullPointerAddNewToDo(){
        assertThrows(NullPointerException.class, ()->toDoService.addNewNote(null));
    }

    @Test
    public void testNullPointerDeleteToDo(){
        assertThrows(NullPointerException.class, ()->toDoService.deleteNoteById(null));
    }

    @Test
    public void testNullPointerUpdateToDo(){
        assertThrows(NullPointerException.class, ()->toDoService.updateNote(null));
    }

    @Test
    public void testNullPointerGetToDo(){
        assertThrows(NullPointerException.class, ()->toDoService.getNoteById(null));
    }
}
