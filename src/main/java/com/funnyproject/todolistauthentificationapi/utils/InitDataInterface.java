package com.funnyproject.todolistauthentificationapi.utils;

import todolist.database.DataInterface;
import todolist.database.mysql.Mysql;

public class InitDataInterface {

    public static DataInterface initDataInterface() {
        return new Mysql(System.getProperty("DB_URL"), System.getProperty("DB_USERNAME"), System.getProperty("DB_PASSWORD"));
    }
}
