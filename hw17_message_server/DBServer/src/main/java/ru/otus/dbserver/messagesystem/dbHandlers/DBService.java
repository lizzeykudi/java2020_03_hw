package ru.otus.dbserver.messagesystem.dbHandlers;

import ru.otus.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface DBService {
    Optional<User> getUser(long id);
}
