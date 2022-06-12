package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;

@Service
public class IdGenerator {

    private volatile int filmCounter = 1;
    private volatile int userCounter = 1;

    public synchronized int generateFilmId() {
        return filmCounter++;
    }

    public synchronized int generateUserId() {
        return userCounter++;
    }

}