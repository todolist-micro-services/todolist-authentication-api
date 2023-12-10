package com.funnyproject.todolistauthentificationapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    private final String serverPort;
    private final String dbUrl;
    private final String dbUserName;
    private final String dbPassword;
    private final String token;
    private final String emailSender;
    private final String emailPassword;

    @Autowired
    public AppConfig(
            @Value("${server.port}") String serverPort,
            @Value("${db.url}") String dbUrl,
            @Value("${db.username}") String dbUserName,
            @Value("${db.password}") String dbPassword,
            @Value("${secret.token}") String token,
            @Value("${email.sender}") String emailSender,
            @Value("${email.password}") String emailPassword
    ) {
        this.emailPassword = emailPassword;
        this.serverPort = serverPort;
        this.dbUrl = dbUrl;
        this.dbPassword = dbPassword;
        this.dbUserName = dbUserName;
        this.token = token;
        this.emailSender = emailSender;
    }

    public String getEmailPassword() {
        return this.emailPassword;
    }

    public String getServerPort() {
        return this.serverPort;
    }

    public String getDbUrl() {
        return this.dbUrl;
    }

    public String getDbUserName() {
        return this.dbUserName;
    }

    public String getDbPassword() {
        return this.dbPassword;
    }

    public String getToken() {
        return this.token;
    }

    public String getEmailSender() {
        return this.emailSender;
    }

}
