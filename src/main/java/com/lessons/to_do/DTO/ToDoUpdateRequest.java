package com.lessons.to_do.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Схема для отправки  запроса на обновление TODO")
public class ToDoUpdateRequest {
    private ToDoRequest toDoRequest;

    @Min(0)
    @Schema(description =  "Id записи ToDO", example =  "1")
    private Long id;

    @Schema(description =  "Дата выполения записи ToDo", example =  "2024-04-30")
    private Date dateOfCompleted;
}
