package com.lessons.to_do.models;


import lombok.*;

import java.io.Serializable;
import java.util.Date;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public final class ToDo implements Serializable {

    private Long id;
    private String title;
    private String description;
    private String content;
    private Date dateOfCreated;
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
