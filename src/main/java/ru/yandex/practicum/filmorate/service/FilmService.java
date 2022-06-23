package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final Storage<Film> filmStorage;
    private final Storage<User> userStorage;

    public void addLike(long filmId, long userId) {
        Film film = filmStorage.getById(filmId);
        User user = userStorage.getById(userId);
        film.getUsersLikes().add(user);
    }

    public void removeLike(long filmId, long userId) {
        Film film = filmStorage.getById(filmId);
        User user = userStorage.getById(userId);
        film.getUsersLikes().remove(user);
    }

    public List<Film> getTopRatedFilms(int count) {
        Collection<Film> popularFilms = filmStorage.getAll();
        return popularFilms.stream()
                .sorted(Comparator.comparingInt(f -> f.getUsersLikes().size() * -1))
                .limit(count)
                .collect(Collectors.toList());
    }
}