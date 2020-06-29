package ru.otus;


import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbcMapper.mapper.JdbcMapperImpl;
import ru.otus.jdbcMapper.model.Account;
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

        demo.createTableUser(dataSource);

        var sessionManager = new SessionManagerJdbc(dataSource);
        DbExecutorImpl<User> dbExecutor = new DbExecutorImpl<>();
        //var userDao = new UserDaoJdbc(sessionManager, dbExecutor);
        var userDao = new JdbcMapperImpl<User>(sessionManager, dbExecutor);

        //var dbServiceUser = new DbServiceUserImpl(userDao);
        var dbServiceUser = new DbServiceImpl<User>(userDao);
        User user = new User("dbServiceUser", 3);
        dbServiceUser.save(user);
        user.setName("updatedName");
        dbServiceUser.update(user);

        Optional<User> userOptional = dbServiceUser.get(1, User.class);



        demo.createTableAccount(dataSource);


        DbExecutorImpl<Account> dbExecutorAccount = new DbExecutorImpl<>();
        var accountDao = new JdbcMapperImpl<Account>(sessionManager, dbExecutorAccount);

        //var dbServiceUser = new DbServiceUserImpl(userDao);
        var dbServiceAccount = new DbServiceImpl<Account>(accountDao);
        Account account = new Account("dbServiceUser", 3);
        dbServiceAccount.insertOrUpdate(account);
        account.setRest(9);
        dbServiceAccount.insertOrUpdate(account);
        Optional<Account> accountOptional = dbServiceAccount.get(1, Account.class);
    }

    private void createTableUser(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("create table user(id long auto_increment, name varchar(50), age int(3))")) {
            pst.executeUpdate();
        }
        System.out.println("table created");
    }

    private void createTableAccount(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("create table account(no long auto_increment, type varchar(50), rest number)")) {
            pst.executeUpdate();
        }
        System.out.println("table created");
    }
}
