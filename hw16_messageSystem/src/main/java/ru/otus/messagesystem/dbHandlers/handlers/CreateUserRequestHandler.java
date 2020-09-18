package ru.otus.messagesystem.dbHandlers.handlers;

import org.hibernate.SessionFactory;
import ru.otus.db.hibernate.cachehw.MyCache;
import ru.otus.domain.model.Address;
import ru.otus.domain.model.Phone;
import ru.otus.db.hibernate.core.model.User;
import ru.otus.db.hibernate.hibernate.HibernateUtils;
import ru.otus.db.hibernate.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.messagesystem.dbHandlers.DBService;
import ru.otus.messagesystem.dto.UserData;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.messagesystem.message.MessageHelper;
import ru.otus.repostory.UserDaoHibernate;
import ru.otus.services.DbServiceUserCache;

import java.util.Optional;


public class CreateUserRequestHandler implements RequestHandler<UserData> {
    private final DBService dbService;

    SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Address.class, Phone.class);
    private final DbServiceUserCache usersService = new DbServiceUserCache(new UserDaoHibernate(new SessionManagerHibernate(sessionFactory)), new MyCache());


    public CreateUserRequestHandler(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        User user = MessageHelper.getPayload(msg);
        long id = usersService.saveUser(user);
        User user1 = usersService.getUser(id).orElseThrow();
        return Optional.of(MessageBuilder.buildReplyMessage(msg, user1));
    }
}

