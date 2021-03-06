package ru.otus.messagesystem.front;

import ru.otus.db.hibernate.core.model.User;
import ru.otus.messagesystem.dto.UserData;
import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.services.DbServiceUserCache;

import java.util.concurrent.ConcurrentLinkedQueue;


public class FrontendServiceImpl implements FrontendService {

    private final DbServiceUserCache usersService;

    private final MsClient msClient;
    private final String databaseServiceClientName;

    public FrontendServiceImpl(DbServiceUserCache usersService, MsClient msClient, String databaseServiceClientName) {
        this.usersService = usersService;
        this.msClient = msClient;
        this.databaseServiceClientName = databaseServiceClientName;
    }

    @Override
    public void getUserData(long userId, MessageCallback<UserData> dataConsumer) {
        Message outMsg = msClient.produceMessage(databaseServiceClientName, new UserData(userId),
                MessageType.USER_DATA, dataConsumer);
        msClient.sendMessage(outMsg);
    }

    @Override
    public void create(User user, MessageCallback<User> dataConsumer) {
        Message outMsg = msClient.produceMessage(databaseServiceClientName, user,
                MessageType.CREATE_USER, dataConsumer);
        msClient.sendMessage(outMsg);
        //usersService.saveUser(user);
    }

}
