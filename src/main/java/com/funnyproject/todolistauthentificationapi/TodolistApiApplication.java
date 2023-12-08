package com.funnyproject.todolistauthentificationapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

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

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);

		mailSender.setUsername(System.getProperty("EMAIL_SENDER"));
		mailSender.setPassword(System.getProperty("EMAIL_PASSWORD"));

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}

}
