package ru.otus.hibernate.core.service;

import ru.otus.hibernate.core.dao.UserDao;
import ru.otus.hibernate.core.model.User;

public class DbServiceFill{
    private final DbServiceUserCache dbServiceUserCache;

    public DbServiceFill(DbServiceUserCache dbServiceUserCache) {
        this.dbServiceUserCache = dbServiceUserCache;
    }

    public void fillDB() {
        for (int i = 0; i < 10; i++) {
            dbServiceUserCache.saveUser(new User(i, "" + i));
        }

    }
}
