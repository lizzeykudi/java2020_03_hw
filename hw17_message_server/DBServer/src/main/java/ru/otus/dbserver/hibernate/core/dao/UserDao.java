package ru.otus.dbserver.hibernate.core.dao;

import ru.otus.dbserver.hibernate.core.sessionmanager.SessionManager;
import ru.otus.domain.model.User;

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
