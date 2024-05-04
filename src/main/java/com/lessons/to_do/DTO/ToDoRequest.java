package com.lessons.to_do.DTO;


import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
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
@Schema(description = "Схема для отправки  запроса на добавление TODO")
public class ToDoRequest {

    @Schema(description = "Название", example = "Задача номер 1")
    @Size(min = 8, max = 255, message = "Длина названия должна быть от 8 до 255 символов")
    @NotBlank(message = "Название не может быть пустыми")
    private String title;

    @Schema(description = "Описание", example = "Задача для...")
    @Size(max = 255, message = "Длина опсиания должна быть не более 255 символов")
    private String description;

    @Schema(description = "Содержание", example = "надо сделать....")
    @Size(min = 8, max = 255, message = "Длина содержания должна быть от 8 до 255 символов")
    @NotBlank(message = "Содержание не может быть пустыми")
    private String content;

}
