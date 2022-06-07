package ru.yandex.practicum.filmorate.model;

public class FilmIdGenerator {
    private FilmIdGenerator(){}
    private static volatile int counter = 1;
    public static synchronized int generateId() {
        return counter++;
    }

}
