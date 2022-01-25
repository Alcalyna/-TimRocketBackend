package com.example.timrocket_backend.api;

import com.example.timrocket_backend.domain.User;
import com.example.timrocket_backend.repository.UserRepository;
import com.example.timrocket_backend.service.dto.CreateUserDTO;
import com.example.timrocket_backend.service.dto.UserDTO;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.http.ContentType.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    UserRepository userRepository;

    @Test
    void endToEndRegisterUser() {
        CreateUserDTO createUserDTO = new CreateUserDTO("Test", "Test", "test@test.aaa", "Testtest1", null);

        RestAssured.defaultParser = Parser.JSON;
        UserDTO userDTO = RestAssured
                .given()
                .body(createUserDTO)
                .accept(JSON)
                .contentType(JSON)
                .when()
                .port(port)
                .post("/members")
                .then().assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(UserDTO.class);

        Assertions.assertThat(userDTO.firstName()).isEqualTo("Test");
        User user = userRepository.getById(userDTO.id());
        Assertions.assertThat(user.getPassword()).isEqualTo("f295398e1493b806b25cd34a73068a31e7e5616f7243b9fe1baead82d6fc5ec8");
    }

    @Test
    void endToEndRegisterUser_whenLastNameIsEmpty() {
        CreateUserDTO createUserDTO = new CreateUserDTO("Test", "", "test@test.aaa", "Testtest1", null);

        RestAssured.defaultParser = Parser.JSON;
        String message = RestAssured
                .given()
                .body(createUserDTO)
                .accept(JSON)
                .contentType(JSON)
                .when()
                .port(port)
                .post("/members")
                .then().assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract()
                .path("lastName");

        Assertions.assertThat(message).isEqualTo("last name should be provided");
    }
}
