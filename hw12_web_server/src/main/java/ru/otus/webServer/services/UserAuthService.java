package ru.otus.webServer.services;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}
