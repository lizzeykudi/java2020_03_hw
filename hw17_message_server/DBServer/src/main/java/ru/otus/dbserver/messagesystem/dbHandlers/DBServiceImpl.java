package ru.otus.dbserver.messagesystem.dbHandlers;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.otus.dbserver.hibernate.cachehw.MyCache;
import ru.otus.dbserver.hibernate.hibernate.HibernateUtils;
import ru.otus.dbserver.hibernate.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.dbserver.repostory.UserDaoHibernate;
import ru.otus.dbserver.services.DbServiceUserCache;
import ru.otus.domain.model.Address;
import ru.otus.domain.model.Phone;
import ru.otus.domain.model.User;

import java.util.Optional;

@Component
public class DBServiceImpl implements DBService {

    public DBServiceImpl(DbServiceUserCache usersService) {
        this.usersService = usersService;
    }

    private final DbServiceUserCache usersService;
    //SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Address.class, Phone.class);
    //private final DbServiceUserCache usersService = new DbServiceUserCache(new UserDaoHibernate(new SessionManagerHibernate(sessionFactory)), new MyCache());
    private static final Logger logger = LoggerFactory.getLogger(DBServiceImpl.class);


    public Optional<User> getUser(long id) {
        logger.info("get data for id:{}", id);
        return usersService.getUser(id);
    }

}
