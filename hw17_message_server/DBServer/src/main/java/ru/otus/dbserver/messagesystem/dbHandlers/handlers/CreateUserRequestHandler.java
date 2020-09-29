package ru.otus.dbserver.messagesystem.dbHandlers.handlers;

import org.springframework.stereotype.Component;
import ru.otus.dbserver.services.DbServiceUserCache;
import ru.otus.domain.model.User;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.ResultDataType;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.messagesystem.message.MessageHelper;

import java.util.Optional;

@Component
public class CreateUserRequestHandler implements RequestHandler<User> {
    private final DbServiceUserCache usersService;


    public CreateUserRequestHandler(DbServiceUserCache usersService) {
        this.usersService = usersService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        User user = MessageHelper.getPayload(msg);
        long id = usersService.saveUser(user);
        User user1 = usersService.getUser(id).orElseThrow();
        return Optional.of(MessageBuilder.buildReplyMessage(msg, user1));
    }
}

