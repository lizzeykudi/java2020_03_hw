package ru.otus.dbserver;


import org.springframework.stereotype.Component;
import ru.otus.dbserver.messagesystem.dbHandlers.DBServiceImpl;
import ru.otus.dbserver.messagesystem.dbHandlers.handlers.CreateUserRequestHandler;
import ru.otus.dbserver.messagesystem.dbHandlers.handlers.GetUserDataRequestHandler;
import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.CallbackRegistryImpl;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.sockets.SocketClient;

@Component
public class DbServerComponent {

    private final SocketClient socketClient = new SocketClient();

    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    public DbServerComponent() {
        init();
    }


  public void init() {
      CallbackRegistry callbackRegistry = new CallbackRegistryImpl();

      HandlersStore requestHandlerDatabaseStore = new HandlersStoreImpl();
      requestHandlerDatabaseStore.addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler(new DBServiceImpl()));
      requestHandlerDatabaseStore.addHandler(MessageType.CREATE_USER, new CreateUserRequestHandler(new DBServiceImpl()));
      MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME,
              socketClient, requestHandlerDatabaseStore, callbackRegistry);



  }
}
