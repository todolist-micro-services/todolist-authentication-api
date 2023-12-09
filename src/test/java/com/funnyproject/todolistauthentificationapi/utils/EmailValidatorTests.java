package com.funnyproject.todolistauthentificationapi.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EmailValidatorTests {

    @Test
    public void goodEmail() throws Exception {
        final String email = "email@email.com";
        final boolean result = EmailValidator.isValidEmail(email);
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void badEmail1() throws Exception {
        final String email = "email";
        final boolean result = EmailValidator.isValidEmail(email);
        assertThat(result).isEqualTo(false);
    }

    @Test
    public void badEmail2() throws Exception {
        final String email = "email.email.com";
        final boolean result = EmailValidator.isValidEmail(email);
        assertThat(result).isEqualTo(false);
    }

}
