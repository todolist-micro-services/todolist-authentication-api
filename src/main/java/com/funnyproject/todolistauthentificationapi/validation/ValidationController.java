package com.funnyproject.todolistauthentificationapi.validation;

import com.funnyproject.todolistauthentificationapi.utils.InitDataInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import todolist.database.DataInterface;
import todolist.database.dataType.Token;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class ValidationController {

    public ValidationController() {
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
        this.dataInterface = InitDataInterface.initDataInterface();
    }

    @GetMapping("/validation/{token}")
    public ResponseEntity<String> validation(@PathVariable String token) {
        Token userToken = this.dataInterface.getUserTokenFromToken(token);
        if (userToken == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Token not found\"}");
        if (userToken.isActivated)
            return ResponseEntity.status(HttpStatus.OK).body("{\"account\": \"Already activated\"}");
        userToken.isActivated = true;
        if (!this.dataInterface.updateUserToken(userToken).isEmpty())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Internal server error\"}");
        return ResponseEntity.status(HttpStatus.OK).body("{\"account\": \"Validate\"}");
    }

    private final DataInterface dataInterface;
}
