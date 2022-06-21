package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.IdGenerator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();

    @Autowired
    private IdGenerator userIdGenerator;

    @Override
    public Collection<User> getAll() {
        log.info("Количество пользователей в настоящий момент - {}", users.size());
        return users.values();
    }

    @Override
    public User getById(long id) {
        log.info("Запрашиваемый объект id №: {}", id);
        return users.values().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь № %d не найден", id)));
    }

    @Override
    public User create(User user) throws ValidationException {
        user.setId(userIdGenerator.generateUserId());
        log.info("Сохраняемый объект: {}", user);
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
    public User update(User user) throws ValidationException {
        log.info("Обновляемый объект: {}", user);
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("Такого пользователя не существует, проверьте правильность или наличие id");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void delete(long id) {
        log.info("Удаляемый объект id №: {}", id);
        if (!users.containsKey(id)) {
            throw new NotFoundException(String.format("Пользователь № %d не найден", id));
        }
        users.remove(id);
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