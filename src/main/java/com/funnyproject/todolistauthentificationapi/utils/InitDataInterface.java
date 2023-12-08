package com.funnyproject.todolistauthentificationapi.utils;

import todolist.database.DataInterface;
import todolist.database.mysql.Mysql;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InitDataInterface {

    public static DataInterface initDataInterface() {
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
        return new Mysql(System.getProperty("DB_URL"), System.getProperty("DB_USERNAME"), System.getProperty("DB_PASSWORD"));
    }
}
