package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.validators.ReleaseDate;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor

public class Film {
    @EqualsAndHashCode.Exclude
    private int id;
    @NotNull @NotBlank (message = "Поле не должно быть пустым")
    private String name;
    @Size(max = 200, message = "Описание не должно превышать 200 символов")
    private String description;
    @ReleaseDate (message = "Дата выхода фильма не должна быть раньше 1895-12-28")
    private LocalDate releaseDate;
    @Positive (message = "Длительность фильма должна быть больше 0")
    private Long duration;
    private int rate;
}