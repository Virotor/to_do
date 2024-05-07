package com.lessons.to_do.repository;

import com.lessons.to_do.models.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {


}
