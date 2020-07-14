package ru.otus.webServer.dao;

import org.hibernate.SessionFactory;
import ru.otus.hibernate.cachehw.MyCache;
import ru.otus.hibernate.core.dao.UserDao;
import ru.otus.hibernate.core.model.Address;
import ru.otus.hibernate.core.model.Phone;
import ru.otus.hibernate.core.model.User;
import ru.otus.hibernate.core.service.DbServiceUserCache;
import ru.otus.hibernate.hibernate.HibernateUtils;
import ru.otus.hibernate.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.List;

public class HibernateUserDao implements ru.otus.webServer.dao.UserDao {

    SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Address.class, Phone.class);
    SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
    UserDao userDao = new UserDaoHibernate(sessionManager);
    DbServiceUserCache dbServiceUserCache = new DbServiceUserCache(userDao, new MyCache<Long, User>());

    public HibernateUserDao() {
        fillDB();
    }


    public void fillDB() {
        for (int i = 0; i < 10; i++) {
            dbServiceUserCache.saveUser(new User(i, "" + i));
        }

    }

    @Override
    public List<User> getAllUsers() {
        return dbServiceUserCache.getAllUsers();
    }

    @Override
    public void saveUser(User user) {
        dbServiceUserCache.saveUser(user);
    }
}
