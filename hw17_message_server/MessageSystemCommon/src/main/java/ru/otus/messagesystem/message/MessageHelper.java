package ru.otus.messagesystem.message;

import java.util.Base64;

public class MessageHelper {
    private MessageHelper() {
    }

    public static <T> T getPayload(Message msg) {
        return (T) Serializers.deserialize(msg.getPayload());
    }

    public static byte[] serializeMessage(Message msg) {
        return Serializers.serialize(msg);
    }
    public static String serializeMessageToString(Message msg) {
        return Base64.getEncoder().encodeToString(Serializers.serialize(msg));
    }

    public static Message deSerializeMessage(byte[] bytes) {
        return (Message) Serializers.deserialize(bytes);
    }
    public static Message deSerializeMessage(String string) {
        return (Message) Serializers.deserialize(Base64.getDecoder().decode(string));
    }
}
