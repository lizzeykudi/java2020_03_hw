package ru.otus.messagesystem.client;

import ru.otus.messagesystem.ResultDataType;

import java.util.function.Consumer;

public interface MessageCallback<T extends ResultDataType> extends Consumer<T> {
}
