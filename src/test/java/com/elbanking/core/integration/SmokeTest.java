package com.elbanking.core.integration;

import com.elbanking.core.application.ElbankingCoreApplication;
import com.elbanking.core.enums.StatusCodeEnum;
import com.elbanking.core.model.HTTPResult;
import com.elbanking.core.model.authentication.AuthRequest;
import com.elbanking.core.model.authentication.AuthResult;
import com.elbanking.core.model.user.RegisterUserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ElbankingCoreApplication.class)
@TestPropertySource(
        locations = "classpath:application-test.properties")
class SmokeTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ObjectMapper objectMapper;

    private final String email = "test123@gmail.com";
    private final String password = "123456";

    @Test
    void smoke_test() throws Exception {
        objectMapper = new ObjectMapper();
        signUp();
        String accessToken = login();


    }

    @AfterEach
    void cleanDatabase(){
        jdbcTemplate.execute("DELETE FROM users" );
        jdbcTemplate.execute("DELETE FROM accounts" );
        jdbcTemplate.execute("DELETE FROM transactions" );
    }

    private void signUp() throws JsonProcessingException {
        String signUpUrl = "http://localhost:" + port + "/signUp";
        String email = "test123@gmail.com";
        String password = "123456";

        RegisterUserRequest registerUserRequest =
                RegisterUserRequest
                        .builder()
                        .email(email)
                        .password(password)
                        .build();

        ResponseEntity<HTTPResult> responseEntity = this.restTemplate.postForEntity(signUpUrl,
                registerUserRequest,
                HTTPResult.class);


        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getBody().getStatus()).isEqualTo(StatusCodeEnum.SUCCESS.getHttpStatusCode());

    }

    private String login() throws JsonProcessingException {
        String loginUrl = "http://localhost:" + port + "/login";
        AuthRequest request = AuthRequest.builder()
                .email(email)
                .password(password)
                .build();

        ResponseEntity<HTTPResult> responseEntity = this.restTemplate.postForEntity(loginUrl,
                request,
                HTTPResult.class);

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getBody().getStatus()).isEqualTo(StatusCodeEnum.SUCCESS.getHttpStatusCode());

        AuthResult authResult = (AuthResult) convertDataToPojo(responseEntity.getBody().getData(),AuthResult.class);

        String accessToken = authResult.getAccessToken();
        Assertions.assertThat(accessToken).isNotNull();

        return accessToken;
    }
    private Object convertDataToPojo(Object data,Class clazz){
        return objectMapper.convertValue(data,clazz);
    }
    private ResponseEntity<HTTPResult> insertTransaction(String accessToken){
        HttpHeaders authHeaders = new HttpHeaders();
        HttpEntity<AuthRequest> authRequestHttpEntity = new HttpEntity<>(authHeaders);

        return null;
    }
    private void setBearerToken(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
    }

    private void printResponseJsonString(ResponseEntity responseEntity){
        try {
            System.out.println(objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(responseEntity));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
