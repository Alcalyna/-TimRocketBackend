package com.example.timrocket_backend.api;

import com.example.timrocket_backend.domain.Member;
import com.example.timrocket_backend.repository.MemberRepository;
import com.example.timrocket_backend.service.dto.CreateMemberDTO;
import com.example.timrocket_backend.service.dto.MemberDTO;
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
public class MemberControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void endToEndRegisterMember() {
        CreateMemberDTO createMemberDTO = new CreateMemberDTO("Test", "Test", "test@test.aaa", "Testtest1", null);

        RestAssured.defaultParser = Parser.JSON;
        MemberDTO memberDTO = RestAssured
                .given()
                .body(createMemberDTO)
                .accept(JSON)
                .contentType(JSON)
                .when()
                .port(port)
                .post("/members")
                .then().assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(MemberDTO.class);

        Assertions.assertThat(memberDTO.firstName()).isEqualTo("Test");
        Member member = memberRepository.getById(memberDTO.id());
        Assertions.assertThat(member.getPassword()).isEqualTo("f295398e1493b806b25cd34a73068a31e7e5616f7243b9fe1baead82d6fc5ec8");
    }
}
