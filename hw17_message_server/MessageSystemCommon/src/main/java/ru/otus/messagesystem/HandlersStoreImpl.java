package ru.otus.messagesystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.otus.messagesystem.ResultDataType;
import ru.otus.messagesystem.message.MessageType;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class HandlersStoreImpl implements HandlersStore {

    @Autowired
    @Qualifier("handlers")
    private final Map<String, RequestHandler<? extends ResultDataType>> handlers;

    public HandlersStoreImpl(Map<String, RequestHandler<? extends ResultDataType>> handlers) {
        this.handlers = handlers;
    }

    @Override
    public RequestHandler<? extends ResultDataType> getHandlerByType(String messageTypeName) {
        return handlers.get(messageTypeName);
    }

    @Override
    public void addHandler(MessageType messageType, RequestHandler<? extends ResultDataType> handler) {
        handlers.put(messageType.getName(), handler);
    }


}
