package ru.yandex.practicum.filmorate.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor

public class User {
    @EqualsAndHashCode.Exclude
    private int id;
    @NotNull
    @NotBlank (message = "Поле не должно быть пустым")
    @Email
    private String email;
    @NotNull @NotBlank (message = "Поле не должно быть пустым")
    private String login;
    private String name;
    @Past
    private LocalDate birthday;
}