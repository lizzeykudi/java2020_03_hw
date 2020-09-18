package ru.otus.frontend.controllers;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.domain.model.User;
import ru.otus.frontend.messagesystem.front.FrontendService;
import ru.otus.frontend.messagesystem.front.FrontendServiceImpl;
import ru.otus.frontend.messagesystem.front.handlers.GetUserDataResponseHandler;
import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.CallbackRegistryImpl;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.sockets.SocketClient;

@Controller
public class UserController {

    private final SimpMessagingTemplate template;

    FrontendService frontendService ;

    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    public UserController(SimpMessagingTemplate template) {
        this.template = template;
        init();
    }

  public void init() {
      //MessageSystem messageSystem = new MessageSystemImpl();
      SocketClient client = new SocketClient();
      CallbackRegistry callbackRegistry = new CallbackRegistryImpl();

      /*HandlersStore requestHandlerDatabaseStore = new HandlersStoreImpl();
      requestHandlerDatabaseStore.addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler(new DBServiceImpl()));
      requestHandlerDatabaseStore.addHandler(MessageType.CREATE_USER, new CreateUserRequestHandler(new DBServiceImpl()));
      MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME,
              client, requestHandlerDatabaseStore, callbackRegistry);
      messageSystem.addClient(databaseMsClient);*/

      HandlersStore requestHandlerFrontendStore = new HandlersStoreImpl();
      requestHandlerFrontendStore.addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler(callbackRegistry));

      MsClient frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME,
              client, requestHandlerFrontendStore, callbackRegistry);
      frontendService = new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
      //messageSystem.addClient(frontendMsClient);
  }




    @MessageMapping("/add")
    @SendTo("/topic/greetings")
    public void greeting(User user) throws Exception {


        frontendService.create(user, responseUser -> this.template.convertAndSend("/topic/allUsers", responseUser));


    }

    //@Scheduled(fixedDelay = 1000)
    public void broadcastCurrentTime() {

    }
}
