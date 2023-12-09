package com.funnyproject.todolistauthentificationapi.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HashPasswordTests {

    @Test
    public void hashPassword() throws Exception {
        final String password = "password";
        final String hashedPassword = HashPassword.hashPassword(password);

        assertThat(password).isNotEqualTo(hashedPassword);
    }

}
