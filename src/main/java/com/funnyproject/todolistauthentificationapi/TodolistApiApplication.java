package com.funnyproject.todolistauthentificationapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

@SpringBootApplication
public class TodolistApiApplication {

	public static void main(String[] args) {
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
		SpringApplication app = new SpringApplication(TodolistApiApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", System.getProperty("SERVER_PORT")));

		app.run(args);
	}

}
