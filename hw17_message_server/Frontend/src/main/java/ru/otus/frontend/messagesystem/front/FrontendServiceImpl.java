package ru.otus.frontend.messagesystem.front;

import org.springframework.stereotype.Component;
import ru.otus.domain.model.User;
import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageType;

@Component
public class FrontendServiceImpl implements FrontendService {

    private final MsClient msClient;
    private final String databaseServiceClientName = "databaseService";

    public FrontendServiceImpl(MsClient msClient) {
        this.msClient = msClient;
    }

    @Override
    public void getUserData(long userId, MessageCallback<User> dataConsumer) {
        Message outMsg = msClient.produceMessage(databaseServiceClientName, new User(userId, null),
                MessageType.USER_DATA, dataConsumer);
        msClient.sendMessage(outMsg);
    }

    @Override
    public void create(User user, MessageCallback<User> dataConsumer) {
        Message outMsg = msClient.produceMessage(databaseServiceClientName, user,
                MessageType.CREATE_USER, dataConsumer);
        msClient.sendMessage(outMsg);
    }

}
