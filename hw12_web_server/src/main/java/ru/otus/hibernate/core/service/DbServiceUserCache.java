package ru.otus.hibernate.core.service;

import org.springframework.stereotype.Service;
import ru.otus.hibernate.cachehw.HwCache;
import ru.otus.hibernate.core.dao.UserDao;
import ru.otus.hibernate.core.model.User;

import java.util.Optional;

@Service
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
