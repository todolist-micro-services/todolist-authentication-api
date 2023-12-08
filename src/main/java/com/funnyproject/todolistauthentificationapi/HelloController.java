package com.funnyproject.todolistauthentificationapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import todolist.database.DataInterface;
import todolist.database.mysql.Mysql;
import todolist.jwttoken.JwtToken;
import todolist.jwttoken.JwtTokenType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@RestController
public class HelloController {

    @GetMapping("/")
    public String index() {
        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/pad/delivery/todoList/todolist-authentification-api/.env"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (System.getProperties().containsKey(parts[0])) {
                    System.getProperties().setProperty(parts[0], parts[1]);
                } else {
                    System.setProperty(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        DataInterface dataInterface = new Mysql(System.getProperty("DB_URL"), System.getProperty("DB_USERNAME"), System.getProperty("DB_PASSWORD"));
//        return String.valueOf(dataInterface.getUser("newemail@example.com", "newpassword"));
        String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
        String name = "John Doe";
        String email = "johndoe@gmail.com";
        String subject = "John";
        int nbrHour = 24;
        JwtTokenType token = JwtToken.createJwtToken(secret, name, email, subject, nbrHour);
        return token.getJwtValue();
    }

}
