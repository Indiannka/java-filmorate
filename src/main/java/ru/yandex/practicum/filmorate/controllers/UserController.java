package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.UserIdGenerator;


import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;

@Validated
@RestController
@RequestMapping("/users")
public class UserController extends AbstractController<User> {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final HashMap<Integer, User> users = new HashMap<>();

    @Override
    @GetMapping
    public Collection<User> getAll() {
        log.info("Количество пользователей в настоящий момент - {}", users.size());
        return users.values();
    }

    @Override
    @PostMapping
    public User create(@Valid @RequestBody User user) throws ValidationException {
        user.setId(UserIdGenerator.generateId());
        log.info("Сохраняемый объект: {}", user );
        if (isExist(user)) {
            log.info("Пользователь с таким email {} уже существует ", user.getEmail());
            throw new ValidationException("Пользователь с таким email уже существует " + user.getEmail());
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    @PutMapping
    public User update(@Valid @RequestBody User user) throws ValidationException {
        log.info("Обновляемый объект: {}", user );
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Такого пользователя не существует, проверьте правильность или наличие id");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        return user;
    }

    private boolean isExist(User user) {
        boolean result = false;
        for (User u : users.values()) {
            if (user.getEmail().equals(u.getEmail())) {
                result = true;
                break;
            }
        }
        return result;
    }
}