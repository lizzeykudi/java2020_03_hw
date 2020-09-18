package ru.otus.dbserver.messagesystem.dbHandlers;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dbserver.hibernate.cachehw.MyCache;
import ru.otus.dbserver.hibernate.hibernate.HibernateUtils;
import ru.otus.dbserver.hibernate.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.dbserver.repostory.UserDaoHibernate;
import ru.otus.dbserver.services.DbServiceUserCache;
import ru.otus.domain.model.Address;
import ru.otus.domain.model.Phone;
import ru.otus.domain.model.User;

import java.util.HashMap;
import java.util.Map;

public class DBServiceImpl implements DBService {
    SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Address.class, Phone.class);
    private final DbServiceUserCache usersService = new DbServiceUserCache(new UserDaoHibernate(new SessionManagerHibernate(sessionFactory)), new MyCache());
    private static final Logger logger = LoggerFactory.getLogger(DBServiceImpl.class);
    private final Map<Long, String> database = new HashMap<>();

    private void initDatabase() {
        database.put(1L, "val1");
        database.put(2L, "val2");
        database.put(3L, "val3");
    }

    public DBServiceImpl() {
        initDatabase();
    }

    public String getUserData(long id) {
        logger.info("get data for id:{}", id);
        return usersService.getAllUsers().toString();
        //return database.get(id);
    }

}
