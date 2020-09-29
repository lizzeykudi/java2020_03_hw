package ru.otus.dbserver.messagesystem.dbHandlers.handlers;

import org.springframework.stereotype.Component;
import ru.otus.dbserver.services.DbServiceUserCache;
import ru.otus.domain.model.User;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.messagesystem.message.MessageHelper;

import java.util.Optional;

@Component
public class GetUserDataRequestHandler implements RequestHandler<User> {
    private final DbServiceUserCache usersService;

    public GetUserDataRequestHandler(DbServiceUserCache usersService) {
        this.usersService = usersService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        User userData = MessageHelper.getPayload(msg);
        User data = new User(userData.getId(), usersService.getUser(userData.getId()).orElseThrow().getName());
        return Optional.of(MessageBuilder.buildReplyMessage(msg, data));
    }
}
