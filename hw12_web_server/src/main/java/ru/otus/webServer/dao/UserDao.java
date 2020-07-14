package ru.otus.webServer.dao;

import ru.otus.webServer.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    default Optional<User> findById(long id) {throw new UnsupportedOperationException();}
    default Optional<User> findRandomUser() {throw new UnsupportedOperationException();}
    default Optional<User> findByLogin(String login) {throw new UnsupportedOperationException();}

    List<ru.otus.hibernate.core.model.User> getAllUsers();

    void saveUser(ru.otus.hibernate.core.model.User user);
}