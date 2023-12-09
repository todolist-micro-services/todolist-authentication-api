package com.funnyproject.todolistauthentificationapi.login;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class LoginRequestTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getVariables() throws Exception {
        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setEmail("email");
        loginRequest.setPassword("password");
        assertThat(loginRequest.getEmail()).isEqualTo("email");
        assertThat(loginRequest.getPassword()).isEqualTo("password");
    }

}
