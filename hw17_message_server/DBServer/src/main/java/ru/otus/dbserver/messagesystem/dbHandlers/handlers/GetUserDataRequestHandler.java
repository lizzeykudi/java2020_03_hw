package ru.otus.dbserver.messagesystem.dbHandlers.handlers;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import ru.otus.dbserver.messagesystem.dbHandlers.DBService;
import ru.otus.dbserver.messagesystem.dto.UserData;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.messagesystem.message.MessageHelper;

import java.util.Optional;

@Component
public class GetUserDataRequestHandler implements RequestHandler<UserData> {
    private final DBService dbService;
    private final Gson gson = new Gson();
    public GetUserDataRequestHandler(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        UserData userData = MessageHelper.getPayload(msg);
        UserData data = new UserData(userData.getUserId(), gson.toJson(dbService.getUser(userData.getUserId())));
        return Optional.of(MessageBuilder.buildReplyMessage(msg, data));
    }
}
