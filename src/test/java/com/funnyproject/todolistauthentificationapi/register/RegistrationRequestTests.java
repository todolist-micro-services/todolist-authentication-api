package com.funnyproject.todolistauthentificationapi.register;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationRequestTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getVariables() throws Exception {
        RegistrationRequest registrationRequest = new RegistrationRequest();

        registrationRequest.setEmail("email");
        registrationRequest.setFirstname("firstname");
        registrationRequest.setLastname("lastname");
        registrationRequest.setPassword("password");
        assertThat(registrationRequest.getEmail()).isEqualTo("email");
        assertThat(registrationRequest.getPassword()).isEqualTo("password");
        assertThat(registrationRequest.getLastname()).isEqualTo("lastname");
        assertThat(registrationRequest.getFirstname()).isEqualTo("firstname");
    }

}
