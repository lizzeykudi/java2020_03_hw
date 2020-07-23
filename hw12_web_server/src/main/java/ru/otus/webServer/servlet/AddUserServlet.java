package ru.otus.webServer.servlet;

import ru.otus.hibernate.core.model.Address;
import ru.otus.hibernate.core.model.User;
import ru.otus.hibernate.core.service.DbServiceUserCache;
import ru.otus.webServer.services.TemplateProcessor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AddUserServlet extends HttpServlet {

    private static final String ADD_USER_PAGE_TEMPLATE = "addPerson.flt";


    private final TemplateProcessor templateProcessor;
    private final DbServiceUserCache userDao;


    public AddUserServlet(TemplateProcessor templateProcessor, DbServiceUserCache userDao) {
        this.templateProcessor = templateProcessor;
        this.userDao = userDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, Object> paramsMap = new HashMap<>();

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(ADD_USER_PAGE_TEMPLATE, paramsMap));

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String address = request.getParameter("address");

        if (null != name && null != address &&
                !name.isEmpty() && !address.isEmpty()) {

            User user = new User();
            user.setName(name);
            user.setAddress(new Address(address));
            userDao.saveUser(user);


        }

        response.sendRedirect("/users");


    }


}
