package com.example.timrocket_backend.api;

import com.example.timrocket_backend.domain.User;
import com.example.timrocket_backend.repository.UserRepository;
import com.example.timrocket_backend.security.SecurityRole;
import com.example.timrocket_backend.service.dto.CreateUserDTO;
import com.example.timrocket_backend.service.dto.UserDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    private String url;
    private String token;
    String userUrlEncoded;

    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String RESET = "\u001B[0m";

    @BeforeEach
    void init() {
        url = "https://keycloak.switchfully.com/auth/realms/java-oct-2021/protocol/openid-connect/token";
        userUrlEncoded = "client_id=CodeCoachTimRocket&username=william&password=william&grant_type=password";

        token = RestAssured.given().auth().preemptive()
                .basic("temp", "temp")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .baseUri("https://keycloak.switchfully.com/auth/realms/java-oct-2021")
                .body(userUrlEncoded)
                .post("/protocol/openid-connect/token")
                .then().extract().path("access_token").toString();
    }

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
                .post("/users")
                .then().assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(UserDTO.class);

        Assertions.assertEquals("Test",userDTO.firstName());
        User user = userRepository.getById(userDTO.id());
        Assertions.assertEquals("f295398e1493b806b25cd34a73068a31e7e5616f7243b9fe1baead82d6fc5ec8",user.getPassword());
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
                .post("/users")
                .then().assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract()
                .path("lastName");

        Assertions.assertEquals("last name should be provided", message);
    }

    @Test
    void getByEmail() {
        User user = new User("Linh", "Calinh", "linh@timrocket.com", "Linhlinh1", "Calinh Corp", SecurityRole.COACHEE);

        userRepository.save(user);

        UserDTO userDTO = RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .port(port)
                .pathParam("email", "linh@timrocket.com")
                .get("/users/{email}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(UserDTO.class);

        Assertions.assertTrue(!userDTO.id().toString().isBlank());
        Assertions.assertEquals("Linh", userDTO.firstName());
        Assertions.assertEquals("Calinh", userDTO.lastName());
        Assertions.assertEquals("linh@timrocket.com", userDTO.email());
        Assertions.assertEquals("Calinh Corp", userDTO.company());
        Assertions.assertEquals(SecurityRole.COACHEE.getRoleName().toUpperCase(), userDTO.role());
        Assertions.assertEquals("assets/default-profile-picture.jpg", userDTO.pictureUrl());
    }

    @Test
    void getById() {
        User user = new User("Ruben", "Tom", "ruben@tom.com", "RubenTom30", null, SecurityRole.COACHEE);

        userRepository.save(user);

        UserDTO userDTO = RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .port(port)
                .queryParam("id", user.getId())
                .get("/users/")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(UserDTO.class);

        Assertions.assertEquals("Ruben", userDTO.firstName());
        Assertions.assertEquals("Tom", userDTO.lastName());
    }
}
