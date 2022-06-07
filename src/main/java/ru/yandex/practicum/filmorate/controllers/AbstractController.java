package ru.yandex.practicum.filmorate.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import javax.validation.Valid;
import java.util.Collection;

abstract class AbstractController<T> {
    @GetMapping
    public abstract Collection<T> getAll();

    @PostMapping
    public abstract T create(@Valid T t) throws ValidationException;

    @PutMapping
    public abstract T update(@Valid T t) throws ValidationException;
}