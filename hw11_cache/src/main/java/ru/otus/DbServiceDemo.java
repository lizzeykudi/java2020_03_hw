package ru.otus;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.MyCache;
import ru.otus.cachehw.MyListener;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.Address;
import ru.otus.core.model.Phone;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserCache;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbServiceDemo {
    private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) {
        // Все главное см в тестах
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Address.class, Phone.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        User user = new User(0, "Вася");
        user.setAddress(new Address("street", user));
        Phone phone = new Phone("899");
        List<Phone> phones = new ArrayList<>();
        phones.add(phone);
        user.setPhones(phones);


        /*DbServiceUserCache dbServiceUserCache = new DbServiceUserCache(userDao);
        for (int i = 0; i < 1000; i++) {
            dbServiceUserCache.saveUser(new User(i, "" + i));
        }

        System.out.println(dbServiceUserCache.cache.getCacheSize()); // 148 */


        long id = dbServiceUser.saveUser(user);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Optional<User> mayBeCreatedUser = dbServiceUser.getUser(id);
        }
        System.out.println("time : " + (double) (System.currentTimeMillis() - start)); //258

        HwCache<Long, User> cache = new MyCache();
        cache.addListener(new MyListener());
        dbServiceUser = new DbServiceUserCache(userDao, cache);
        id = dbServiceUser.saveUser(user);
        start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Optional<User> mayBeCreatedUser = dbServiceUser.getUser(id);
        }
        System.out.println("time : " + (double) (System.currentTimeMillis() - start)); //1

    }


}
