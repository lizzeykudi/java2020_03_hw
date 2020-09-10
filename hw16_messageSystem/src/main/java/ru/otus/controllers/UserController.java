package ru.otus.controllers;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static ru.otus.ApplConfig.DATE_TIME_FORMAT;

@Controller
public class UserController {

    private final SimpMessagingTemplate template;
    ConcurrentLinkedQueue<Long> queue = new ConcurrentLinkedQueue();

    private final DbServiceUserCache usersService;
    FrontendService frontendService;

    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    public UserController(SimpMessagingTemplate template, DbServiceUserCache usersService) {
        this.template = template;
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
      frontendService = new FrontendServiceImpl(usersService, frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
      messageSystem.addClient(frontendMsClient);
  }




    @MessageMapping("/add")
    @SendTo("/topic/greetings")
    public void greeting(User user) throws Exception {
        queue.offer(frontendService.create(user));
    }

    @Scheduled(fixedDelay = 1000)
    public void broadcastCurrentTime() {
        Long id = queue.poll();
        if (id!=null) {
        User user = new User();
        user.setId(id);
        frontendService.getUserData(id, data -> {user.setName(data.getData());});
        this.template.convertAndSend("/allUsers", user);}
    }
}
