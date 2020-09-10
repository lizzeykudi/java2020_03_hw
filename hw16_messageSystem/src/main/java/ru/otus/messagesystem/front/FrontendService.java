package ru.otus.messagesystem.front;

import ru.otus.db.hibernate.core.model.User;
import ru.otus.messagesystem.dto.UserData;
import ru.otus.messagesystem.client.MessageCallback;

public interface FrontendService {
    void getUserData(long userId, MessageCallback<UserData> dataConsumer);

    long create(User user);
}

