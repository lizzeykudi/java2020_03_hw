package ru.otus.jdbcMapper.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDaoException;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.dao.UserDaoJdbc;
import ru.otus.jdbc.mapper.JdbcMapper;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;
import ru.otus.jdbcMapper.annotationProcessing.DaoClass;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

    private final SessionManagerJdbc sessionManager;
    private final DbExecutorImpl<T> dbExecutor;

    public JdbcMapperImpl(SessionManagerJdbc sessionManager, DbExecutorImpl<T> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }
    @Override
    public void insert(T objectData) {
        try {
            dbExecutor.executeInsert(getConnection(), "insert into "+objectData.getClass().getSimpleName()+" values (?)",
                    new DaoClass(objectData).getValuesWithoutId());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void update(T objectData) {

    }

    @Override
    public void insertOrUpdate(T objectData) {

    }

    @Override
    public Optional<T> findById(long id, Class<T> clazz) {
        try {
            return dbExecutor.executeSelect(getConnection(), "select * from " + clazz.getName() + " where id  = ?",
                    id, rs -> {
                        try {
                            new DaoClass(clazz.newInstance()).getObjectFromResultSet(rs, clazz);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        }
                        return null;
                    });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return  Optional.empty();
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

}
