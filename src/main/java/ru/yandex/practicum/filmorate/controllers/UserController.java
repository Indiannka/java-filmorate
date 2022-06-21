package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.Storage;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final Storage<User> storage;
    private final UserService userService;

    @GetMapping
    public Collection<User> getAll() {
        return storage.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable("id") long userId) {
        return storage.getById(userId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getAllFriends(@PathVariable("id") long userId) {
        return userService.getAllFriends(userId);
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return storage.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) throws ValidationException {
        return storage.update(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long userId) {
        storage.delete(userId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") long userId, @PathVariable long friendId) {
        log.info("REST request to add id:{} as a user's: {} friend", userId, friendId);
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable("id") long userId, @PathVariable long friendId) {
        log.info("REST request to delete id:{} as a user's: {} friend", userId, friendId);
        userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable("id") long userId, @PathVariable long otherId) {
        log.info("REST request to getCommonFriends id:{} as a user's: {} other user", userId, otherId);
        return userService.getCommonFriends(userId, otherId);
    }
}