package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;

@Service
public class IdGenerator {

    private volatile long filmCounter = 1;
    private volatile long userCounter = 1;

    public synchronized long generateFilmId() {
        return filmCounter++;
    }

    public synchronized long generateUserId() {
        return userCounter++;
    }

}