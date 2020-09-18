package ru.otus.frontend.messagesystem.front;

import ru.otus.domain.model.User;
import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.messagesystem.dto.UserData;

public interface FrontendService {
    void getUserData(long userId, MessageCallback<UserData> dataConsumer);

    void create(User user, MessageCallback<User> dataConsumer);
}

