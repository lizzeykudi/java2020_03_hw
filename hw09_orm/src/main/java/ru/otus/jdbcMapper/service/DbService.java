package ru.otus.jdbcMapper.service;

import java.util.Optional;

public interface DbService<T> {

    void save(T t);

    void insertOrUpdate(T t);

    void update(T t);

    Optional<T> get(long id, Class<T> clazz);
}
