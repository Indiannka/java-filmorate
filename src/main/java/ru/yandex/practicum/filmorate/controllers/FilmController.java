package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.IdGenerator;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();

    @Autowired
    IdGenerator filmIdGenerator;

    @GetMapping
    public Collection<Film> getAll() {
        log.info("Количество фильмов в настоящий момент - {}", films.size());
        return films.values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        film.setId(filmIdGenerator.generateFilmId());
        log.info("Сохраняемый объект: {}", film);
        if (films.containsValue(film)) {
            throw new ValidationException("Такой фильм уже существует");
        } else films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) throws ValidationException {
        log.info("Обновляемый объект: {}", film);
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Такого фильма не существует. Проверьте правильность или наличие id");
        }
        films.put(film.getId(), film);
        return film;
    }
}