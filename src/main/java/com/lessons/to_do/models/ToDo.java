package com.lessons.to_do.models;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;



@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "note")
public final class ToDo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "content")
    private String content;

    @Column(name = "date_create")
    private Date dateOfCreated;

    @Column(name = "date_completed")
    private Date dateOfCompleted;


    @Override
    public String toString() {
        return  "Id " + id + "   "
                + "Note with name " + title + "  "
                + "Description " + description + "   "
                + "Content " + content + "   "
                + "Date create" + dateOfCreated + "  "
                + "Date of complete " + (dateOfCompleted == null ? "Not completed" : dateOfCompleted.toString()) +"\n";
    }
}
