package ru.yandex.practicum.filmorate.model;

public class UserIdGenerator {
    private UserIdGenerator(){}
    private static volatile int counter = 1;
    public static synchronized int generateId() {
        return counter++;
    }
}