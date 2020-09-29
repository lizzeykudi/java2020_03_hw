package ru.otus.dbserver.hibernate.core.service;

import ru.otus.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);

    List<User> getAllUsers();

}
