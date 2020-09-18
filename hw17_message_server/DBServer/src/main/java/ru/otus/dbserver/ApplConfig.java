package ru.otus.dbserver;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.otus.dbserver.hibernate.hibernate.HibernateUtils;
import ru.otus.domain.model.Address;
import ru.otus.domain.model.Phone;
import ru.otus.domain.model.User;

@Configuration
@EnableScheduling
@ComponentScan(basePackages = "ru.otus.dbserver")
public class ApplConfig {
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public SessionFactory sessionFactory() {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Address.class, Phone.class);
        return sessionFactory;
    }
}
