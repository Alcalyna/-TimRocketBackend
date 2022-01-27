package com.example.timrocket_backend.api;

import com.example.timrocket_backend.domain.User;
import com.example.timrocket_backend.repository.UserRepository;
import com.example.timrocket_backend.security.SecurityRole;
import com.example.timrocket_backend.service.dto.CoachDTO;
import com.example.timrocket_backend.service.dto.CreateUserDTO;
import com.example.timrocket_backend.service.dto.UserDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import java.util.Collections;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    UserRepository userRepository;

    private String url;
    private String coachToken;

    private String coacheeToken;

    String userUrlEncoded;
    String coacheeUrlEncoded;


    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String RESET = "\u001B[0m";

    @BeforeEach
    void init() {
        url = "https://keycloak.switchfully.com/auth/realms/java-oct-2021/protocol/openid-connect/token";
        userUrlEncoded = "client_id=CodeCoachTimRocket&username=william&password=william&grant_type=password";
        coacheeUrlEncoded = "client_id=CodeCoachTimRocket&username=linh@timrocket.com&password=Linhlinh1&grant_type=password";

        coachToken = RestAssured.given().auth().preemptive()
                .basic("temp", "temp")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .baseUri("https://keycloak.switchfully.com/auth/realms/java-oct-2021")
                .body(userUrlEncoded)
                .post("/protocol/openid-connect/token")
                .then().extract().path("access_token").toString();


        coacheeToken = RestAssured.given().auth().preemptive()
                .basic("temp", "temp")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .baseUri("https://keycloak.switchfully.com/auth/realms/java-oct-2021")
                .body(coacheeUrlEncoded)
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
        User user = userRepository.getById(userDTO.userId());
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
                .header("Authorization", "Bearer " + coachToken)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .port(port)
                .queryParam("email", "linh@timrocket.com")
                .get("/users/")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(UserDTO.class);

        Assertions.assertTrue(!userDTO.userId().toString().isBlank());
        Assertions.assertEquals("Linh", userDTO.firstName());
        Assertions.assertEquals("Calinh", userDTO.lastName());
        Assertions.assertEquals("linh@timrocket.com", userDTO.email());
        Assertions.assertEquals("Calinh Corp", userDTO.company());
        Assertions.assertEquals(SecurityRole.COACHEE.getRoleName().toUpperCase(), userDTO.role());
        Assertions.assertEquals("assets/default-profile-picture.jpg", userDTO.pictureUrl());
    }

    @Test
    void getAllUsers() {
        User user1 = new User("Linh", "Calinh", "linh@timrocket.com", "Linhlinh1", "Calinh Corp", SecurityRole.COACHEE);
        User user2 = new User("Léonie", "Bouchat", "leo@timrocket.com", "Leoleo11", "Tim Roquet", SecurityRole.COACHEE);

        userRepository.save(user1);
        userRepository.save(user2);

        List<UserDTO> userDTOList = RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .port(port)
                .get("/users")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", UserDTO.class);

        assertThat(userDTOList.size() > 1);
    }

    @Test
    void getById() {
        User user = new User("Ruben", "Tom", "ruben@tom.com", "RubenTom30", null, SecurityRole.COACHEE);

        userRepository.save(user);

        UserDTO userDTO = RestAssured
                .given()
                .header("Authorization", "Bearer " + coachToken)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .port(port)
                .pathParam("id", user.getId())
                .get("/users/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(UserDTO.class);

        Assertions.assertEquals("Ruben", userDTO.firstName());
        Assertions.assertEquals("Tom", userDTO.lastName());
    }

    @Test
    @DisplayName("Given an authorized user, when getting coach by id, then return the coach information")
    void getCoachByIdAsAuthorizedUser() {

        CoachDTO coachDTO = RestAssured
                .given()
                .header("Authorization", "Bearer " + coachToken)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .port(port)
                .pathParam("id", "96f7383e-67c1-4cb9-936c-a046d13f7fec")
                .get("/users/coach/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CoachDTO.class);

        Assertions.assertEquals("Tom", coachDTO.user().firstName());
        Assertions.assertEquals( "Dit is een test introductie" , coachDTO.coachInformation().introduction());
        Assertions.assertNotEquals( Collections.EMPTY_LIST, coachDTO.coachTopics());
    }

    @Test
    @DisplayName("Given an unauthorized user, when getting coach by id, then return 403 forbidden")
    void getErrorWhenGetCoachByIdAsUnauthorizedUser() {

        RestAssured
                .given()
                .header("Authorization", "Bearer " + coacheeToken)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .port(port)
                .pathParam("id", "96f7383e-67c1-4cb9-936c-a046d13f7fec")
                .get("/users/coach/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());

    }
}
