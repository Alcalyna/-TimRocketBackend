package com.example.timrocket_backend.api;

import com.example.timrocket_backend.domain.User;
import com.example.timrocket_backend.repository.UserRepository;
import com.example.timrocket_backend.security.SecurityRole;
import com.example.timrocket_backend.service.dto.CoachDTO;
import com.example.timrocket_backend.service.dto.CreateUserDTO;
import com.example.timrocket_backend.service.dto.UpdateUserDTO;
import com.example.timrocket_backend.service.dto.UserDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

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

    String coachUrlEncoded;
    String coacheeUrlEncoded;


    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String RESET = "\u001B[0m";

    @BeforeEach
    void init() {
        url = "https://keycloak.switchfully.com/auth/realms/java-oct-2021/protocol/openid-connect/token";

        coachUrlEncoded = "client_id=CodeCoachTimRocket&username=coach@keycloacktoken.com&password=Coach123&grant_type=password";
        coacheeUrlEncoded = "client_id=CodeCoachTimRocket&username=coachee@keycloacktoken.com&password=Coachee123&grant_type=password";

        coachToken = RestAssured.given().auth().preemptive()
                .basic("temp", "temp")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .baseUri("https://keycloak.switchfully.com/auth/realms/java-oct-2021")
                .body(coachUrlEncoded)
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
        CreateUserDTO createUserDTO = new CreateUserDTO("Test", "Test", "test@test.aaa", "Testtest1");

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

        Assertions.assertEquals("Test", userDTO.firstName());
        User user = userRepository.getById(userDTO.userId());
        Assertions.assertEquals("f295398e1493b806b25cd34a73068a31e7e5616f7243b9fe1baead82d6fc5ec8", user.getPassword());
    }

    @Test
    void endToEndRegisterUser_whenLastNameIsEmpty() {
        CreateUserDTO createUserDTO = new CreateUserDTO("Test", "", "test@test.aaa", "Testtest1");

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
        User user = new User("Linh", "Calinh", "blinh@timrocket.com", "Linhlinh1", SecurityRole.COACHEE);

        userRepository.save(user);

        UserDTO userDTO = RestAssured
                .given()
                .header("Authorization", "Bearer " + coachToken)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .port(port)
                .queryParam("email", "blinh@timrocket.com")
                .get("/users/")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(UserDTO.class);

        Assertions.assertTrue(!userDTO.userId().toString().isBlank());
        Assertions.assertEquals("Linh", userDTO.firstName());
        Assertions.assertEquals("Calinh", userDTO.lastName());
        Assertions.assertEquals("blinh@timrocket.com", userDTO.email());
        Assertions.assertEquals(SecurityRole.COACHEE.getRoleName().toLowerCase(), userDTO.role().toLowerCase());
        Assertions.assertEquals("assets/default-profile-picture.jpg", userDTO.pictureUrl());
    }

    @Test
    void getAllUsers() {

        List<CoachDTO> userDTOList = RestAssured
                .given()
                .header("Authorization", "Bearer " + coachToken)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .port(port)
                .get("/users?coach=")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", CoachDTO.class);

        assertThat(userDTOList.size() > 1);
    }

    @Test
    void getById() {
        User user = new User("Ruben", "Tom", "ruben@tom.com", "RubenTom30", SecurityRole.COACHEE);

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
                .get("/users/{id}?coach=")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CoachDTO.class);

        Assertions.assertEquals("Tom", coachDTO.user().firstName());
        Assertions.assertEquals("Dit is een test introductie", coachDTO.coachInformation().introduction());
        Assertions.assertNotEquals(Collections.EMPTY_LIST, coachDTO.coachTopics());
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
                .get("/users/{id}?coach=")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void endToEndUpdateUser() {
        User user = new User("Ruben", "Tom", "coachee@keycloacktoken.com", "RubenTom30", SecurityRole.COACHEE);
        User savedUser = userRepository.save(user);

        UpdateUserDTO updateUserDTO = new UpdateUserDTO("RubenUpdate", "TomUpdate", "coachee@keycloacktoken.com", SecurityRole.COACHEE.getRoleName(), "testpicture.jpg");

        String savedUserId = savedUser.getId().toString();

        RestAssured.defaultParser = Parser.JSON;
        UserDTO userDTO = RestAssured
                .given()
                .header("Authorization", "Bearer " + coacheeToken)
                .body(updateUserDTO)
                .accept(JSON)
                .contentType(JSON)
                .when()
                .port(port)
                .pathParam("id", savedUserId)
                .put("/users/{id}")
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(UserDTO.class);

        Assertions.assertEquals("RubenUpdate", userDTO.firstName());
        Assertions.assertEquals("coachee@keycloacktoken.com", userDTO.email());
        Assertions.assertEquals("coachee", userDTO.role().toLowerCase());
    }

    @Test
    void endToEndUserUpdateWithDifferentUsers() {
        User user = new User("Ruben", "Tom", "coach@keycloacktoken.com", "RubenTom30", SecurityRole.COACHEE);
        User savedUser = userRepository.save(user);

        UpdateUserDTO updateUserDTO = new UpdateUserDTO("RubenUpdate", "TomUpdate", "coachee@keycloacktoken.com", SecurityRole.COACHEE.getRoleName(), "testpicture.jpg");

        String exception = RestAssured
                .given()
                .header("Authorization", "Bearer " + coachToken)
                .body(updateUserDTO)
                .accept(JSON)
                .contentType(JSON)
                .when()
                .port(port)
                .pathParam("id", "2ac5b3fc-95df-40fb-8cab-b994bee20f0c")
                .put("/users/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .extract().path("message");

        Assertions.assertEquals("You are not allowed to edit this profile!", exception);
    }
}
