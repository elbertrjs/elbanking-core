package com.elbanking.core.integration;

import com.elbanking.core.application.ElbankingCoreApplication;
import com.elbanking.core.constant.account.AccountConstant;
import com.elbanking.core.enums.StatusCodeEnum;
import com.elbanking.core.enums.TransactionTypeEnum;
import com.elbanking.core.model.HTTPResult;
import com.elbanking.core.model.authentication.AuthRequest;
import com.elbanking.core.model.authentication.AuthResult;
import com.elbanking.core.model.transaction.InsertTransactionRequest;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
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

        insertTransaction(accessToken);

    }

    @AfterEach
    void cleanDatabase(){
        jdbcTemplate.execute("DELETE FROM users" );
        jdbcTemplate.execute("DELETE FROM accounts" );
        jdbcTemplate.execute("DELETE FROM transactions" );
    }

    private void signUp() throws JsonProcessingException {
        String url = buildUrl("/signUp");
        String email = "test123@gmail.com";
        String password = "123456";

        RegisterUserRequest registerUserRequest =
                RegisterUserRequest
                        .builder()
                        .email(email)
                        .password(password)
                        .build();

        ResponseEntity<HTTPResult> responseEntity = this.restTemplate.postForEntity(url,
                registerUserRequest,
                HTTPResult.class);


        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getBody().getStatus()).isEqualTo(StatusCodeEnum.SUCCESS.getHttpStatusCode());

    }

    private String login() throws JsonProcessingException {
        String url = buildUrl("/login");
        AuthRequest request = AuthRequest.builder()
                .email(email)
                .password(password)
                .build();

        ResponseEntity<HTTPResult> responseEntity = this.restTemplate.postForEntity(url,
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
    private Long insertTransaction(String accessToken){
        String url = buildUrl("/transactions");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        Long creditAmount = 7500000L;
        Long debitAmount = 5000000L;

        InsertTransactionRequest creditRequest = InsertTransactionRequest
                .builder()
                .accessToken(accessToken)
                .amount(7500000L)
                .type(TransactionTypeEnum.CREDIT.name())
                .note("Electricity bill")
                .build();

        InsertTransactionRequest debitRequest = InsertTransactionRequest
                .builder()
                .accessToken(accessToken)
                .amount(5000000L)
                .type(TransactionTypeEnum.DEBIT.name())
                .note("Freelance payment")
                .build();

        HttpEntity<InsertTransactionRequest> creditHTTPEntity = new HttpEntity<>(creditRequest,headers);
        HttpEntity<InsertTransactionRequest> debitHTTPEntity = new HttpEntity<>(debitRequest,headers);

        ResponseEntity<HTTPResult> creditResponseEntity = this.restTemplate.exchange(url, HttpMethod.POST,creditHTTPEntity,HTTPResult.class);
        ResponseEntity<HTTPResult> debitResponseEntity = this.restTemplate.exchange(url, HttpMethod.POST,debitHTTPEntity,HTTPResult.class);

        Assertions.assertThat(creditResponseEntity).isNotNull();
        Assertions.assertThat(creditResponseEntity.getBody().getStatus()).isEqualTo(StatusCodeEnum.SUCCESS.getHttpStatusCode());

        Assertions.assertThat(debitResponseEntity).isNotNull();
        Assertions.assertThat(debitResponseEntity.getBody().getStatus()).isEqualTo(StatusCodeEnum.SUCCESS.getHttpStatusCode());
        Long expectedRemainingBalance = AccountConstant.INITIAL_BALANCE - creditAmount + debitAmount;
        return expectedRemainingBalance;
    }

    private void printResponseJsonString(ResponseEntity responseEntity){
        try {
            System.out.println(objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(responseEntity));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    private String buildUrl(String endpoint){
        return "http://localhost:" + port + endpoint;
    }
}
