package ru.otus.jdbcMapper.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.model.User;
import ru.otus.core.service.DbServiceException;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.jdbc.mapper.JdbcMapper;

import java.lang.reflect.ParameterizedType;
import java.util.Optional;

public class DbServiceImpl<T> implements DbService<T>{
    private static final Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

    private final JdbcMapper jdbcMapper;

    public DbServiceImpl(JdbcMapper jdbcMapper) {
        this.jdbcMapper = jdbcMapper;
    }
    @Override
    public void save(T t) {
        try (var sessionManager = jdbcMapper.getSessionManager()) {
            sessionManager.beginSession();
            try {
                jdbcMapper.insert(t);
                sessionManager.commitSession();

                logger.info("created user: {}", t);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public void insertOrUpdate(T t) {
        try (var sessionManager = jdbcMapper.getSessionManager()) {
            sessionManager.beginSession();
            try {
                jdbcMapper.insertOrUpdate(t);
                sessionManager.commitSession();

                logger.info("createdOrUpdate user: {}", t);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public void update(T t) {
        try (var sessionManager = jdbcMapper.getSessionManager()) {
            sessionManager.beginSession();
            try {
                jdbcMapper.update(t);
                sessionManager.commitSession();

                logger.info("updated user: {}", t);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<T> get(long id, Class<T> clazz) {
        try (var sessionManager = jdbcMapper.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<T> userOptional = jdbcMapper.findById(id, clazz);

                logger.info("user: {}", userOptional.orElse(null));
                return userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
