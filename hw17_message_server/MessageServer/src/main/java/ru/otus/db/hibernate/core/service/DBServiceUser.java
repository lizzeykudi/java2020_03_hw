package ru.otus.db.hibernate.core.service;

import ru.otus.db.hibernate.core.model.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);

    List<User> getAllUsers();

}
