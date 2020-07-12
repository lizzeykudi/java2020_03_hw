package ru.otus.hibernate.core.dao;

import ru.otus.hibernate.core.model.User;
import ru.otus.hibernate.core.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id);

    List<User> getAll();

    long insertUser(User user);

    void updateUser(User user);

    void insertOrUpdate(User user);

    SessionManager getSessionManager();
}
