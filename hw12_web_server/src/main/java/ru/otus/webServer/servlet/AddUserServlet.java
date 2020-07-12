package ru.otus.webServer.servlet;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hibernate.cachehw.HwCache;
import ru.otus.hibernate.cachehw.MyCache;
import ru.otus.hibernate.cachehw.MyListener;
import ru.otus.hibernate.core.dao.UserDao;
import ru.otus.hibernate.core.model.Address;
import ru.otus.hibernate.core.model.Phone;
import ru.otus.hibernate.core.model.User;
import ru.otus.hibernate.core.service.DBServiceUser;
import ru.otus.hibernate.core.service.DbServiceUserCache;
import ru.otus.hibernate.core.service.DbServiceUserImpl;
import ru.otus.hibernate.hibernate.HibernateUtils;
import ru.otus.hibernate.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.hibernate.sessionmanager.SessionManagerHibernate;
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

/*<!--<table class="datatable">-->
<!--    <tr>-->
<!--        <th>Firstname</th>  <th>Lastname</th>-->
<!--    </tr>-->
<!--    <#list users as user>-->
<!--    <tr>-->
<!--        <td>${user.firstname}</td> <td>${user.lastname}</td>-->
<!--    </tr>-->
<!--</#list>-->
<!--</table>-->*/

public class AddUserServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "addPerson.flt";
    private static final String TEMPLATE_ATTR_RANDOM_USER = "randomUser";


    private final TemplateProcessor templateProcessor;

    SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Address.class, Phone.class);
    SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
    UserDao userDao = new UserDaoHibernate(sessionManager);
    DbServiceUserCache dbServiceUserCache = new DbServiceUserCache(userDao, new MyCache<Long, User>());

    public AddUserServlet(TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        fillDB();
        Map<String, Object> paramsMap = new HashMap<>();
        List list = new ArrayList();
        list.add("lol");
        paramsMap.put(TEMPLATE_ATTR_RANDOM_USER, getAllUsers());
        //userDao.findRandomUser().ifPresent(randomUser -> paramsMap.put(TEMPLATE_ATTR_RANDOM_USER, list));
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));



        //Put the user list in request and
        //let freemarker paint it.

        //request.setAttribute("users", "null");

        //request.getRequestDispatcher(USERS_PAGE_TEMPLATE).forward(request, response);

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
            dbServiceUserCache.saveUser(user);

            /*synchronized(userList) {
                userList.add(new User(firstname, lastname));
            }*/

        }

        //doGet(request, response);
    }

    private void fillDB() {


        for (int i = 0; i < 10; i++) {
            dbServiceUserCache.saveUser(new User(i, "" + i));
        }




    }

    private List<User> getAllUsers() {
        return dbServiceUserCache.getAllUsers();
    }

}
