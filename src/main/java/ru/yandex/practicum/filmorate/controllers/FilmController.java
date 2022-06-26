package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.Storage;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final Storage<Film> filmStorage;
    private final FilmService filmService;

    @GetMapping
    public Collection<Film> getAll() {
        return filmStorage.getAll();
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable("id") long filmId) {
        return filmStorage.getById(filmId);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return filmStorage.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return filmStorage.update(film);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long filmId) {
        filmStorage.delete(filmId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable("id") long filmId, @PathVariable long userId) {
        log.info("REST запрос на удаление Like фильма с id:{} пользователем id: {}", userId, userId);
        filmService.removeLike(filmId, userId);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable("id") long filmId, @PathVariable long userId) {
        log.info("REST запрос на добавление Like фильму с id:{} пользователем id: {}", userId, userId);
        filmService.addLike(filmId, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getTopRatedFilms(@RequestParam(defaultValue = "10", required = false) Integer count) {
        log.info("REST запрос списка популярных фильмов в количестве: {}", count);
        if (count <= 0) {
            throw new ValidationException("Список фильмов должен быть равен значению больше 0");
        }
        return filmService.getTopRatedFilms(count);
    }
}