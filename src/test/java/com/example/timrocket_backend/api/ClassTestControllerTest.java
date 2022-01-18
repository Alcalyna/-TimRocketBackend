package com.example.timrocket_backend.api;

import com.example.timrocket_backend.domain.ClassTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClassTestControllerTest {
    @LocalServerPort
    private int port;

    private String url;
    private String token;
    String userUrlEncoded;

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

        ClassTest classTest = new ClassTest("Julinh", "Juda");

        ClassTest res = RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .body(classTest)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .port(port)
                .post("/classtests")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ClassTest.class);
    }

    @Test
    void getAllClassTests() {
        List<ClassTest> res = RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .port(port)
                .get("/classtests")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(List.class);

        Assertions.assertTrue(res.size() > 0);
    }

    @Test
    void addClassTest() {
        ClassTest classTest = new ClassTest("Tomen", "Runie");

       ClassTest res = RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
               .body(classTest)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .port(port)
                .post("/classtests")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ClassTest.class);

       Assertions.assertEquals("Tomen", res.getFirstName());
       Assertions.assertEquals("Runie", res.getSecondName());
    }
}