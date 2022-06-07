package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmIdGenerator;

import javax.validation.Valid;
import java.util.*;

@Validated
@RestController
@RequestMapping("/films")
public class FilmController extends AbstractController<Film> {

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    @GetMapping
    public Collection<Film> getAll() {
        log.info("Количество фильмов в настоящий момент - {}", films.size());
        return films.values();
    }

    @Override
    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        film.setId(FilmIdGenerator.generateId());
        log.info("Сохраняемый объект: {}", film);
        if (isExist(film)) {
            throw new ValidationException("Такой фильм уже существует");
        } else films.put(film.getId(), film);
        return film;
    }

    @Override
    @PutMapping
    public Film update(@Valid @RequestBody Film film ) throws ValidationException {
        log.info("Обновляемый объект: {}", film);
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Такого фильма не существует. Проверьте правильность или наличие id");
        }
        films.put(film.getId(), film);
        return film;
    }

    private boolean isExist(Film film) {
        boolean result = false;
        for (Film f : films.values()) {
            if (film.equals(f)) {
                result = true;
                break;
            }
        }
        return result;
    }
}