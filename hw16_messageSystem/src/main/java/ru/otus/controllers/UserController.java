package ru.otus.controllers;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.db.hibernate.core.model.User;
import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageSystemImpl;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.CallbackRegistryImpl;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.dbHandlers.DBServiceImpl;
import ru.otus.messagesystem.dbHandlers.handlers.GetUserDataRequestHandler;
import ru.otus.messagesystem.front.FrontendService;
import ru.otus.messagesystem.front.FrontendServiceImpl;
import ru.otus.messagesystem.front.handlers.GetUserDataResponseHandler;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.services.DbServiceUserCache;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final DbServiceUserCache usersService;
    FrontendService frontendService;

    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    public UserController(DbServiceUserCache usersService) {
        this.usersService = usersService;
        init();
    }

  public void init() {
      MessageSystem messageSystem = new MessageSystemImpl();
      CallbackRegistry callbackRegistry = new CallbackRegistryImpl();

      HandlersStore requestHandlerDatabaseStore = new HandlersStoreImpl();
      requestHandlerDatabaseStore.addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler(new DBServiceImpl()));
      MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME,
              messageSystem, requestHandlerDatabaseStore, callbackRegistry);
      messageSystem.addClient(databaseMsClient);

      HandlersStore requestHandlerFrontendStore = new HandlersStoreImpl();
      requestHandlerFrontendStore.addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler(callbackRegistry));

      MsClient frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME,
              messageSystem, requestHandlerFrontendStore, callbackRegistry);
      frontendService = new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
      messageSystem.addClient(frontendMsClient);
  }




    @MessageMapping("/add")
    @SendTo("/topic/greetings")
    public User greeting(User user) throws Exception {
        usersService.saveUser(user);
        return user;
    }
}
