package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class User {

    private int id;
    private String name;

    @Past
    private LocalDate birthday;

    @NotNull
    @NotBlank(message = "Поле не должно быть пустым")
    private String login;

    @Email
    @NotNull
    @NotBlank(message = "Поле не должно быть пустым")
    private String email;
}