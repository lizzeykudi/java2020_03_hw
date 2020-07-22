package ru.otus.hibernate.core.service;

import ru.otus.hibernate.core.dao.UserDao;
import ru.otus.hibernate.core.model.User;

public class DbServiceFill extends DbServiceUserImpl{
    public DbServiceFill(UserDao userDao) {
        super(userDao);
    }

    public void fillDB() {
        for (int i = 0; i < 10; i++) {
            saveUser(new User(i, "" + i));
        }

    }
}
