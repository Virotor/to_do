package com.lessons.to_do.repository;


import com.lessons.to_do.models.ToDo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ToDoRepository {

    private final SessionFactory sessionFactory;


    @Cacheable(value = "noteAll", key = "#root.targetClass")
    public List<ToDo> getAllNote() {
        log.info("Получение всех пользователей");
        return sessionFactory.openSession().createSelectionQuery ("from ToDo", ToDo.class).list();
    }

    @Cacheable(value = "note", key = "#id")
    public Optional<ToDo> getNoteById(@NotNull Long id) {
        log.info("Получение записи с id " + id);
        return Optional.ofNullable(sessionFactory.openSession().get(ToDo.class, id));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "note", key = "#id"),
                    @CacheEvict(value = "noteAll", allEntries = true, key = "#root.targetClass")
            }
    )
    public ToDo deleteNoteById(@NotNull Long id) {
        log.info("Удаление записи с id " + id);
        Session session = sessionFactory.openSession();
        try{
            var res = getNoteById(id);
            if(res.isPresent()){
                Transaction tx1 = session.beginTransaction();
                session.remove(res.get());
                tx1.commit();
                session.close();
            }
            else {
                throw  new IllegalArgumentException("Todo not found =");
            }
            return  res.get();
        }
        catch (IllegalArgumentException e){
            log.error("Todo not found = " + id);
            return null;
        }
    }

    @Caching(
            put = {
                    @CachePut(value = "note", key="#toDoToUpdate.id"),

            },
            evict = {
                    @CacheEvict(value = "noteAll", key = "#root.targetClass")
            }
    )
    public ToDo updateNote(@NotNull ToDo toDoToUpdate) {
        log.info("Обновление записи " + toDoToUpdate);
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        var result = session.merge(toDoToUpdate);
        tx1.commit();
        session.close();
        return result;
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "noteAll", key = "#root.targetClass")
            }
    )
    public ToDo insertNote(@NotNull ToDo toDoToInsert) {
        log.info("Добавление записи  " + toDoToInsert);
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        ToDo toDo = ToDo.builder()
                .title(toDoToInsert.getTitle())
                .content(toDoToInsert.getContent())
                .description(toDoToInsert.getDescription())
                .dateOfCreated(new Date())
                .dateOfCompleted(toDoToInsert.getDateOfCompleted())
                .build();
        session.persist(toDo);
        tx1.commit();
        session.close();
        return toDo;
    }
}
