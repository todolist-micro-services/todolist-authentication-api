package com.funnyproject.todolistauthentificationapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import todolist.database.DataInterface;
import todolist.database.mysql.Mysql;

@RestController
public class HelloController {

    @Value("${DB_URL}")
    private String databaseUrl;

    @Value("${DB_USERNAME}")
    private String databaseUser;

    @Value("${DB_PASSWORD}")
    private String databasePassword;

    @GetMapping("/")
    public String index() {
        DataInterface dataInterface = new Mysql(databaseUrl, databaseUser, databasePassword);
        return String.valueOf(dataInterface.getUser("newemail@example.com", "newpassword"));
    }

}
