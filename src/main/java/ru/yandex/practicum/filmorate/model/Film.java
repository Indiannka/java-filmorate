package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.yandex.practicum.filmorate.validators.ReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "name", "description", "releaseDate", "duration"}, callSuper = false)
public class Film extends BaseEntity {

    public static final String MAX_DESCRIPTION_LENGTH = "Описание не должно превышать 200 символов";
    public static final String MIN_RELEASE_DATE = "Дата выхода фильма не должна быть раньше 1895-12-28";
    public static final String MIN_DURATION = "Длительность фильма должна быть больше 0";

    private Long id;

    @NotNull
    @NotBlank(message = "Поле не должно быть пустым")
    private String name;

    @Size(max = 200, message = MAX_DESCRIPTION_LENGTH)
    private String description;

    @ReleaseDate(message = MIN_RELEASE_DATE)
    private LocalDate releaseDate;

    @Positive(message = MIN_DURATION)
    private Long duration;

    @JsonIgnore
    @ToString.Exclude
    private Set<User> usersLikes = new HashSet<>();

    private int rate;
}