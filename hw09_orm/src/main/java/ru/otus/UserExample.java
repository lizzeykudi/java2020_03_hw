package ru.otus;


import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbcMapper.mapper.JdbcMapperImpl;
import ru.otus.jdbcMapper.model.User;
import ru.otus.jdbcMapper.service.DbServiceImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

public class UserExample {

    public static void main(String[] args) throws Exception {
        var dataSource = new DataSourceH2();
        var demo = new UserExample();

        demo.createTable(dataSource);

        var sessionManager = new SessionManagerJdbc(dataSource);
        DbExecutorImpl<User> dbExecutor = new DbExecutorImpl<>();
        //var userDao = new UserDaoJdbc(sessionManager, dbExecutor);
        var userDao = new JdbcMapperImpl<User>(sessionManager, dbExecutor);

        //var dbServiceUser = new DbServiceUserImpl(userDao);
        var dbServiceUser = new DbServiceImpl<User>(userDao);
        dbServiceUser.save(new User(0, "dbServiceUser", 3));
        Optional<User> user = dbServiceUser.get(0, User.class);
    }

    private void createTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("create table user(id long auto_increment, name varchar(50), age int(3))")) {
            pst.executeUpdate();
        }
        System.out.println("table created");
    }
}
