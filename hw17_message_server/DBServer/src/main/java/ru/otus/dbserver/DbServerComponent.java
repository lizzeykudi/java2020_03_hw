package ru.otus.dbserver;


import ru.otus.dbserver.messagesystem.dbHandlers.handlers.CreateUserRequestHandler;
import ru.otus.dbserver.messagesystem.dbHandlers.handlers.GetUserDataRequestHandler;
import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.sockets.SocketClient;


public class DbServerComponent {

    private final int PORT;
    private final String HOST;


    private final HandlersStore requestHandlerDatabaseStore;


    GetUserDataRequestHandler getUserDataRequestHandler;


    CreateUserRequestHandler createUserRequestHandler;

    public DbServerComponent(int PORT, String HOST, HandlersStore requestHandlerDatabaseStore, GetUserDataRequestHandler getUserDataRequestHandler, CreateUserRequestHandler createUserRequestHandler) {
        this.PORT = PORT;
        this.HOST = HOST;
        this.requestHandlerDatabaseStore = requestHandlerDatabaseStore;
        this.getUserDataRequestHandler = getUserDataRequestHandler;
        this.createUserRequestHandler = createUserRequestHandler;

        requestHandlerDatabaseStore.addHandler(MessageType.USER_DATA, getUserDataRequestHandler);
        requestHandlerDatabaseStore.addHandler(MessageType.CREATE_USER, createUserRequestHandler);
    }
}
