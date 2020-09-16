package ru.otus.services;

import org.springframework.stereotype.Service;
import ru.otus.db.hibernate.cachehw.HwCache;
import ru.otus.db.hibernate.core.dao.UserDao;
import ru.otus.db.hibernate.core.model.User;
import ru.otus.db.hibernate.core.service.DbServiceUserImpl;

import java.util.Optional;

@Service
public class DbServiceUserCache extends DbServiceUserImpl {

    private HwCache<Long, User> cache;
    public DbServiceUserCache(UserDao userDao, HwCache<Long, User> cache) {
        super(userDao);
        this.cache = cache;
        fillDB();
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

    private void fillDB() {
        for (int i = 0; i < 10; i++) {
            saveUser(new User(i, "" + i));
        }

    }
}
