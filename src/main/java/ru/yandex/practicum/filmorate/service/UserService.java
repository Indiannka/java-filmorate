package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final Storage<User> userStorage;

    public void addFriend(long userId, long friendId) {
        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);
        user.getFriends().add(friend.getId());
        friend.getFriends().add(user.getId());
    }

    public void deleteFriend(long userId, Long friendId) {
        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(user.getId());
    }

    public Collection<User> getAllFriends(long userId) {
        return userStorage.getById(userId).getFriends().stream()
                .map(userStorage::getById).collect(Collectors.toList());
    }

    public Collection<User> getCommonFriends(long userId, long otherId) {
        List<Long> friends = new ArrayList<>(userStorage.getById(userId).getFriends());
        List<Long> otherFriends = new ArrayList<>(userStorage.getById(otherId).getFriends());
        friends.retainAll(otherFriends);
        return friends.stream().map(userStorage::getById).collect(Collectors.toList());
    }
}