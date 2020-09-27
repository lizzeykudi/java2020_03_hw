package ru.otus.messagesystem.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.messagesystem.CallbackId;
import ru.otus.messagesystem.ResultDataType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CallbackRegistryImpl implements CallbackRegistry {

    @Autowired
    public CallbackRegistryImpl() {
    }

    private final Map<CallbackId, MessageCallback<? extends ResultDataType>> callbackRegistry = new ConcurrentHashMap<>();

    @Override
    public void put(CallbackId id, MessageCallback<? extends ResultDataType> callback) {
        callbackRegistry.put(id, callback);
    }

    @Override
    public MessageCallback<? extends ResultDataType> getAndRemove(CallbackId id) {
        return callbackRegistry.remove(id);
    }
}
