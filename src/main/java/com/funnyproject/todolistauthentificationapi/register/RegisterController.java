package com.funnyproject.todolistauthentificationapi.register;

import com.funnyproject.todolistauthentificationapi.AppConfig;
import com.funnyproject.todolistauthentificationapi.utils.EmailValidator;
import com.funnyproject.todolistauthentificationapi.utils.HashPassword;
import com.funnyproject.todolistauthentificationapi.utils.InitDataInterface;
import com.funnyproject.todolistauthentificationapi.utils.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import todolist.database.DataInterface;
import todolist.database.dataType.Token;
import todolist.database.dataType.User;
import todolist.jwttoken.JwtToken;
import todolist.jwttoken.JwtTokenType;

@RestController
@RequestMapping("/auth")
public class RegisterController {

    private final AppConfig appConfig;

    public RegisterController(AppConfig appConfig) {
        this.appConfig = appConfig;
        dataInterface = InitDataInterface.initDataInterface(appConfig.getDbUrl(), appConfig.getDbUserName(), appConfig.getDbPassword());
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest registrationRequest) {
        ResponseEntity<String> response = this.checkParameters(registrationRequest);
        if (response != null)
            return response;
        String firstname = registrationRequest.getFirstname();
        String lastname = registrationRequest.getLastname();
        String email = registrationRequest.getEmail();
        String password = HashPassword.hashPassword(registrationRequest.getPassword());
        User user = new User(0, firstname, lastname, email, password);
        String dataInterfaceResponse = dataInterface.createUser(user);

        if (dataInterfaceResponse.isEmpty())
            return this.createToken(dataInterface, email, password, firstname);
        if (dataInterfaceResponse.contains("Duplicate"))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"Email already register\"}");
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Internal server error\"}");
    }

    private ResponseEntity<String> createToken(DataInterface dataInterface, String email, String password, String name) {
        final String responseMessage = String.format("{\"message\": \"Email sent to %s\"}", email);
        final int userId = dataInterface.getUser(email, password);
        if (userId == -1 || userId == -2)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Internal server error\"}");
        final String secret = appConfig.getToken();
        final int nbrHour = 24;
        final JwtTokenType token = JwtToken.createJwtToken(secret, name, email, name, nbrHour);
        final Token dbToken = new Token(0, userId, token.getJwtValue(), token.getExpirationDate(), false);
        if (!dataInterface.createUserToken(dbToken).isEmpty())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Internal server error\"}");
        try {
            emailService.sendEmail(email, "Account validation - todolist-micro-service", "http://localhost:8081/auth/validation/" + token.getJwtValue());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Internal server error - cannot send email\"}");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    private ResponseEntity<String> checkParameters(RegistrationRequest registrationRequest) {
        try {
            validateRegistrationRequest(registrationRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("{\"error\": \"Missing parameters, needs : firstname, lastname, email, password\"}");
        }

        String email = registrationRequest.getEmail();
        if (!EmailValidator.isValidEmail(email))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"Bad email form\"}");
        return null;
    }

    private void validateRegistrationRequest(RegistrationRequest registrationRequest) {
        if (registrationRequest == null ||
                registrationRequest.getFirstname() == null ||
                registrationRequest.getLastname() == null ||
                registrationRequest.getEmail() == null ||
                registrationRequest.getPassword() == null) {
            throw new IllegalArgumentException("Missing required parameters");
        }
    }

    private final DataInterface dataInterface;

    @Autowired
    private SendEmail emailService;
}
