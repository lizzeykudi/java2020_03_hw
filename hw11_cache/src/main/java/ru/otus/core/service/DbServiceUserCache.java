package ru.otus.core.service;

import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.MyCache;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;

import java.util.Optional;

public class DbServiceUserCache extends DbServiceUserImpl{

    private HwCache<Long, User> cache;
    public DbServiceUserCache(UserDao userDao, HwCache<Long, User> cache) {
        super(userDao);
        this.cache = cache;
    }

    @Override
    public long saveUser(User user) {
        long id = super.saveUser(user);
        cache.put(id, user);
        return id;
    }

    @Override
    public Optional<User> getUser(long id) {
        User user = cache.get(id);
        if (user!=null) {return Optional.of(user);}
        else {return super.getUser(id);}
    }
}
