package ru.otus.webServer.servlet;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hibernate.cachehw.HwCache;
import ru.otus.hibernate.cachehw.MyCache;
import ru.otus.hibernate.cachehw.MyListener;
import ru.otus.hibernate.core.model.Address;
import ru.otus.hibernate.core.model.Phone;
import ru.otus.hibernate.core.model.User;
import ru.otus.hibernate.core.service.DBServiceUser;
import ru.otus.hibernate.core.service.DbServiceUserCache;
import ru.otus.hibernate.core.service.DbServiceUserImpl;
import ru.otus.hibernate.hibernate.HibernateUtils;
import ru.otus.hibernate.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.webServer.dao.HibernateUserDao;
import ru.otus.webServer.dao.UserDao;
import ru.otus.webServer.services.TemplateProcessor;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllUsersServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "users.html";
    private static final String TEMPLATE_ATTR_RANDOM_USER = "randomUser";


    private final TemplateProcessor templateProcessor;
    private final UserDao userDao = new HibernateUserDao();
    public AllUsersServlet(TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();

        paramsMap.put(TEMPLATE_ATTR_RANDOM_USER, userDao.getAllUsers());

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }





}
