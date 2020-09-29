package ru.otus.dbserver;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.otus.dbserver.hibernate.hibernate.HibernateUtils;
import ru.otus.dbserver.messagesystem.dbHandlers.handlers.CreateUserRequestHandler;
import ru.otus.dbserver.messagesystem.dbHandlers.handlers.GetUserDataRequestHandler;
import ru.otus.domain.model.Address;
import ru.otus.domain.model.Phone;
import ru.otus.domain.model.User;
import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.ResultDataType;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.sockets.SocketClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@ComponentScan(basePackages = {"ru.otus.dbserver", "ru.otus.messagesystem"})
public class ApplConfig {
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    @Bean
    @Qualifier("handlers")
    public Map<String, RequestHandler<? extends ResultDataType>> map(GetUserDataRequestHandler getUserDataRequestHandler, CreateUserRequestHandler createUserRequestHandler) {
        Map<String, RequestHandler<? extends ResultDataType>> objectObjectConcurrentHashMap = new ConcurrentHashMap<>();
        objectObjectConcurrentHashMap.put(MessageType.USER_DATA.getName(), getUserDataRequestHandler);
        objectObjectConcurrentHashMap.put(MessageType.CREATE_USER.getName(), createUserRequestHandler);
        return objectObjectConcurrentHashMap;
    }

    @Bean
    public SocketClient socketClient() {
        return new SocketClient(8100, "localhost");
    }


    @Bean
    public MsClient msClient(SocketClient messageSystem, HandlersStore handlersStore, CallbackRegistry callbackRegistry) {
        return new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem, handlersStore, callbackRegistry);
    }

    @Bean
    public SessionFactory sessionFactory() {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Address.class, Phone.class);
        return sessionFactory;
    }
}
