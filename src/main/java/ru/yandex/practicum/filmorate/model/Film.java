package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validators.ReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Film {

    public static final String MAX_DESCRIPTION_LENGTH = "Описание не должно превышать 200 символов";
    public static final String MIN_RELEASE_DATE = "Дата выхода фильма не должна быть раньше 1895-12-28";
    public static final String MIN_DURATION = "Длительность фильма должна быть больше 0";

    private int id;

    @NotNull
    @NotBlank(message = "Поле не должно быть пустым")
    private String name;

    @Size(max = 200, message = MAX_DESCRIPTION_LENGTH)
    private String description;

    @ReleaseDate(message = MIN_RELEASE_DATE)
    private LocalDate releaseDate;

    @Positive(message = MIN_DURATION)
    private Long duration;

    private int rate;
}