package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.IdGenerator;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();

    @Autowired
    private IdGenerator filmIdGenerator;

    @Override
    public Collection<Film> getAll() {
        log.info("Количество фильмов в настоящий момент - {}", films.size());
        return films.values();
    }

    @Override
    public Film getById(long id) {
        return films.values().stream()
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format("Фильм № %d не найден", id)));
    }

    @Override
    public Film create(Film film) throws ValidationException {
        film.setId(filmIdGenerator.generateFilmId());
        log.info("Сохраняемый объект: {}", film);
        if (films.containsValue(film)) {
            throw new ValidationException("Такой фильм уже существует");
        } else films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(@Valid @RequestBody Film film) throws ValidationException {
        log.info("Обновляемый объект: {}", film);
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Такого фильма не существует. Проверьте правильность или наличие id");
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void delete(long id) {
        log.info("Удаляемый объект id №: {}", id);
        if (!films.containsKey(id)) {
            throw new NotFoundException(String.format("Фильм № %d не найден", id));
        }
        films.remove(id);
    }
}