package ru.otus.frontend.controllers;


import org.springframework.beans.factory.annotation.Autowired;
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



    private final FrontendService frontendService ;

    @Autowired
    public UserController(SimpMessagingTemplate template, FrontendService frontendService) {
        this.frontendService = frontendService;
        this.template = template;


    }

    @MessageMapping("/add")
    @SendTo("/topic/greetings")
    public void greeting(User user) throws Exception {


        frontendService.create(user, responseUser -> this.template.convertAndSend("/topic/allUsers", responseUser));


    }
}
