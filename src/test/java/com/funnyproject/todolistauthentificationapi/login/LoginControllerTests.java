package com.funnyproject.todolistauthentificationapi.login;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void requestWithNoParameters() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/auth/login").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
    }

    @Test
    public void requestWithParameters() throws Exception {
        final String requestBody = "{\"email\": \"email@email.com\", \"password\": \"testpassword\"}";
        mvc.perform(MockMvcRequestBuilders.post("/auth/register").content(requestBody).accept(MediaType.ALL))
                .andExpect(status().isUnsupportedMediaType());
    }

}
