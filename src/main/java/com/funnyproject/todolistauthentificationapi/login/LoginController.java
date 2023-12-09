package com.funnyproject.todolistauthentificationapi.login;

import com.funnyproject.todolistauthentificationapi.utils.HashPassword;
import com.funnyproject.todolistauthentificationapi.utils.InitDataInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import todolist.database.DataInterface;
import todolist.database.dataType.Token;
import todolist.jwttoken.JwtToken;
import todolist.jwttoken.JwtTokenType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class LoginController {

    public LoginController() {
        try (BufferedReader reader = new BufferedReader(new FileReader(".env"))) {
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
        dataInterface = InitDataInterface.initDataInterface();
    }

    @PostMapping("/login")
    public ResponseEntity<String> register(@RequestBody LoginRequest loginRequest) {
        try {
            validateLoginRequest(loginRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("{\"error\": \"Missing parameters, needs : email, password\"}");
        }
        final String email = loginRequest.getEmail();
        final String password = HashPassword.hashPassword(loginRequest.getPassword());
        final int userId = dataInterface.getUser(email, password);

        if (userId == -1)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"User not found with these credentials\"}");
        if (userId == -2)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Internal server error\"}");
        final Token token = dataInterface.getUserToken(userId);
        if (!token.isActivated)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"You must activate your account with the link send to your email\"}");
        return generateNewToken(userId, email);
    }

    private void validateLoginRequest(LoginRequest loginRequest) {
        if (loginRequest == null ||
                loginRequest.getEmail() == null ||
                loginRequest.getPassword() == null) {
            throw new IllegalArgumentException("Missing required parameters");
        }
    }

    private ResponseEntity<String> generateNewToken(final int userId, final String email) {
        final String secret = System.getProperty("SECRET_TOKEN");
        final int nbrHour = 24;
        final JwtTokenType token1 = JwtToken.createJwtToken(secret, email, email, email, nbrHour);
        final Token token2 = new Token(0, userId, token1.getJwtValue(), token1.getExpirationDate(), true);
        String responseMessage = "";

        if (!dataInterface.deleteUserToken(userId).isEmpty() || !dataInterface.createUserToken(token2).isEmpty())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Internal server error\"}");
        responseMessage = String.format("{\"token\": \"%s\", \"expiration\": \"%s\"}", token2.jwtValue, token2.expirationDate.toString());
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    private final DataInterface dataInterface;
}
