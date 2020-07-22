package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import ru.otus.hibernate.cachehw.MyCache;
import ru.otus.hibernate.core.dao.UserDao;
import ru.otus.hibernate.core.model.Address;
import ru.otus.hibernate.core.model.Phone;
import ru.otus.hibernate.core.model.User;
import ru.otus.hibernate.core.service.DbServiceFill;
import ru.otus.hibernate.core.service.DbServiceUserCache;
import ru.otus.hibernate.hibernate.HibernateUtils;
import ru.otus.hibernate.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.webServer.server.UsersWebServer;
import ru.otus.webServer.server.UsersWebServerSimple;
import ru.otus.webServer.services.TemplateProcessor;
import ru.otus.webServer.services.TemplateProcessorImpl;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
@Component
public class WebServerSimpleDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    private final ApplicationContext applicationContext;

    @Autowired
    public WebServerSimpleDemo(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static void main(String[] args) throws Exception {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Address.class, Phone.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DbServiceFill dbServiceFill = new DbServiceFill(userDao);
        dbServiceFill.fillDB();
        DbServiceUserCache dbServiceUserCache = new DbServiceUserCache(userDao, new MyCache<Long, User>());

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        UsersWebServer usersWebServer = new UsersWebServerSimple(WEB_SERVER_PORT, dbServiceUserCache,
                gson, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }


}
