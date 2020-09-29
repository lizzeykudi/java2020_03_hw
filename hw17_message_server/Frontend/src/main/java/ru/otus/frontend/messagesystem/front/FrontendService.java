package ru.otus.frontend.messagesystem.front;

import ru.otus.domain.model.User;
import ru.otus.messagesystem.client.MessageCallback;

public interface FrontendService {
    void getUserData(long userId, MessageCallback<User> dataConsumer);

    void create(User user, MessageCallback<User> dataConsumer);
}

