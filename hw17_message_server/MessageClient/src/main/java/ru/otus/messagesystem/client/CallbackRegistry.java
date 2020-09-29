package ru.otus.messagesystem.client;

import ru.otus.messagesystem.CallbackId;
import ru.otus.messagesystem.ResultDataType;

public interface CallbackRegistry {
    void put(CallbackId id, MessageCallback<? extends ResultDataType> callback);

    MessageCallback<? extends ResultDataType> getAndRemove(CallbackId id);
}
