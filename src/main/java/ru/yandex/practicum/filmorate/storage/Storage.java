package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.BaseEntity;

import java.util.Collection;

public interface Storage<T extends BaseEntity> {

    Collection<T> getAll();

    T getById(long id);

    T create(T entity);

    T update(T entity);

    void delete(long id);

}