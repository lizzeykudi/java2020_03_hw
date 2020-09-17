package ru.otus.messagesystem.dbHandlers;

import ru.otus.db.hibernate.core.model.User;

import java.util.List;

public interface DBService {
    String getUserData(long id);
}
