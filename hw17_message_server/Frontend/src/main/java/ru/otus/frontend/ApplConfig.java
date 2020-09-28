package ru.otus.frontend;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import ru.otus.frontend.messagesystem.front.handlers.GetUserDataResponseHandler;
import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.ResultDataType;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.sockets.SocketClient;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableWebSocketMessageBroker
@ComponentScan({"ru.otus.frontend", "ru.otus.messagesystem"})
public class ApplConfig implements WebSocketMessageBrokerConfigurer {
    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Bean
    @Qualifier("handlers")
    public Map<String, RequestHandler<? extends ResultDataType>> map(GetUserDataResponseHandler getUserDataResponseHandler) {
        Map<String, RequestHandler<? extends ResultDataType>> objectObjectConcurrentHashMap = new ConcurrentHashMap<>();
        objectObjectConcurrentHashMap.put(MessageType.USER_DATA.getName(), getUserDataResponseHandler);
        return objectObjectConcurrentHashMap;
    }

    @Bean
    public SocketClient socketClient() {
        return new SocketClient(8100, "localhost");
    }

    @Bean
    public MsClient msClient(SocketClient messageSystem, HandlersStore handlersStore, CallbackRegistry callbackRegistry) {
        return new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem, handlersStore, callbackRegistry);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }



    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
        registry.addEndpoint("/add").withSockJS();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(DATE_TIME_FORMAT);
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        };
    }

}
