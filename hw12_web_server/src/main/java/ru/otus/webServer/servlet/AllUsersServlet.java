package ru.otus.webServer.servlet;

import ru.otus.hibernate.core.service.DbServiceUserCache;
import ru.otus.webServer.services.TemplateProcessor;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AllUsersServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "users.html";
    private static final String TEMPLATE_USERS = "users";


    private final TemplateProcessor templateProcessor;
    private final DbServiceUserCache userDao;
    public AllUsersServlet(TemplateProcessor templateProcessor, DbServiceUserCache userDao) {
        this.templateProcessor = templateProcessor;
        this.userDao = userDao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();

        paramsMap.put(TEMPLATE_USERS, userDao.getAllUsers());

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }





}
