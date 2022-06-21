package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final Storage<User> storage;

    public void addFriend(long userId, long friendId) {
        User user = storage.getById(userId);
        User friend = storage.getById(friendId);
        addFriend(user, friend);
    }

    public void deleteFriend(long userId, long friendId) {
        User user = storage.getById(userId);
        User friend = storage.getById(friendId);
        deleteFriend(user, friend);
    }

    public Collection<User> getAllFriends(long userId) {
        return new ArrayList<>(storage.getById(userId).getFriends());
    }

    public Collection<User> getCommonFriends(long userId, long otherId) {
        List<User> friends = new ArrayList<>(storage.getById(userId).getFriends());
        List<User> otherFriends = new ArrayList<>(storage.getById(otherId).getFriends());
        friends.retainAll(otherFriends);
        return friends;
    }

    private void addFriend(User user, User friend) {
        user.getFriends().add(friend);
        friend.getFriends().add(user);
    }

    private void deleteFriend(User user, User friend) {
        user.getFriends().remove(friend);
        friend.getFriends().remove(user);
    }
}