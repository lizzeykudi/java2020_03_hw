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

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
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
            long id = dbExecutor.executeInsert(getConnection(), "insert into " + objectData.getClass().getSimpleName() + " values (?,?,?)",
                    new DaoClass(objectData).getValuesWithoutId());
            new DaoClass(objectData).setIdValue(objectData, id);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void update(T objectData) {
        try {
            String sql = "";
            List<Field> fieldsWithoutId = new DaoClass(objectData).getFieldsWithoutId();
            for (Field field : fieldsWithoutId) {
                sql += field.getName() + " = ?, ";
            }
            sql = sql.substring(0, sql.length() - 2);

            dbExecutor.executeUpdate(getConnection(), "update " + objectData.getClass().getSimpleName() + " set " + sql + " where " + new DaoClass(objectData).getIdFieldName() + " = " + new DaoClass(objectData).getIdFieldValue(),
                    new DaoClass(objectData).getValuesForUpdate());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void insertOrUpdate(T objectData) {
        long idFieldValue = new DaoClass(objectData).getIdFieldValue();
        Optional<T> byId = findById(idFieldValue, (Class<T>) objectData.getClass());
        if (byId.isPresent()) {
            update(objectData);}
        else {
            insert(objectData);}
    }


    @Override
    public Optional<T> findById(long id, Class<T> clazz) {
        try {
            return dbExecutor.executeSelect(getConnection(), "select * from " + clazz.getSimpleName() + " where " + new DaoClass(clazz.newInstance()).getIdFieldName() + "  = ?",
                    id, rs -> {
                        try {
                            return (T) new DaoClass(clazz.newInstance()).getObjectFromResultSet(rs, clazz);
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
        return Optional.empty();
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

}
