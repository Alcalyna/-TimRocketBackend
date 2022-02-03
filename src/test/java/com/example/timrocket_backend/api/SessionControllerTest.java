package com.example.timrocket_backend.api;

import com.example.timrocket_backend.service.dto.session.CreateSessionDTO;
import com.example.timrocket_backend.service.dto.session.SessionDTO;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static io.restassured.http.ContentType.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SessionControllerTest {

    @LocalServerPort
    private int port;

    private String url;
    private String coachToken;
    private String coacheeToken;

    String coachUrlEncoded;
    String coacheeUrlEncoded;


    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String RESET = "\u001B[0m";

    @BeforeAll
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
    void endToEndCreateSession() {
        CreateSessionDTO createSessionDTO = new CreateSessionDTO("Test Session", LocalDate.now(), LocalTime.now(), "online", null, "Test session for end to end test", UUID.fromString("8ec18d0f-1ae6-4dee-9191-4848b4dea8f6"), UUID.fromString("96f7383e-67c1-4cb9-936c-a046d13f7fec"));

        RestAssured.defaultParser = Parser.JSON;
        SessionDTO sessionDTO = RestAssured
                .given()
                .header("Authorization", "Bearer " + coachToken)
                .body(createSessionDTO)
                .accept(JSON)
                .contentType(JSON)
                .when()
                .port(port)
                .post("/sessions")
                .then().assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(SessionDTO.class);

        Assertions.assertEquals("Test Session", sessionDTO.subject());
        Assertions.assertNotNull(sessionDTO.sessionId());
    }

    @Test
    void endToEndCreateSessionWithDateInThePastThenReturnErrorMessage() {
        CreateSessionDTO createSessionDTO = new CreateSessionDTO("Test Session", LocalDate.now().minusDays(3), LocalTime.now(), "online", null, "Test session for end to end test", UUID.fromString("8ec18d0f-1ae6-4dee-9191-4848b4dea8f6"), UUID.fromString("96f7383e-67c1-4cb9-936c-a046d13f7fec"));

        RestAssured.defaultParser = Parser.JSON;
        String message = RestAssured
                .given()
                .header("Authorization", "Bearer " + coachToken)
                .body(createSessionDTO)
                .accept(JSON)
                .contentType(JSON)
                .when()
                .port(port)
                .post("/sessions")
                .then().assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract()
                .path("date");

        Assertions.assertEquals("date should not be in the past", message);
    }
}
