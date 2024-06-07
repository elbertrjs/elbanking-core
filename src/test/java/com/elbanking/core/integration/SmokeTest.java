package com.elbanking.core.integration;

import com.elbanking.core.application.ElbankingCoreApplication;
import com.elbanking.core.constant.account.AccountConstant;
import com.elbanking.core.enums.StatusCodeEnum;
import com.elbanking.core.enums.TransactionTypeEnum;
import com.elbanking.core.model.HTTPResult;
import com.elbanking.core.model.account.QueryAccountRequest;
import com.elbanking.core.model.account.QueryAccountResult;
import com.elbanking.core.model.authentication.AuthRequest;
import com.elbanking.core.model.authentication.AuthResult;
import com.elbanking.core.model.transaction.InsertTransactionRequest;
import com.elbanking.core.model.transaction.QueryTransactionRequest;
import com.elbanking.core.model.transaction.QueryTransactionResult;
import com.elbanking.core.model.transaction.TransactionView;
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

    private final Long creditAmount = 7500000L;
    private final Long debitAmount = 5000000L;

    @Test
    void smoke_test() throws Exception {
        objectMapper = new ObjectMapper();
        // sign up new user
        signUp();

        // login user
        String accessToken = login();

        // insert withdrawal and deposit
        insertTransaction(accessToken);

        // get user transaction statement
        getStatement(accessToken);

        // get current user balance and check if the balance is correct
        getAccount(accessToken);
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
    private void insertTransaction(String accessToken){
        String url = buildUrl("/transactions");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);


        InsertTransactionRequest creditRequest = InsertTransactionRequest
                .builder()
                .accessToken(accessToken)
                .amount(creditAmount)
                .type(TransactionTypeEnum.CREDIT.name())
                .note("Electricity bill")
                .build();

        InsertTransactionRequest debitRequest = InsertTransactionRequest
                .builder()
                .accessToken(accessToken)
                .amount(debitAmount)
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
    }

    private void getStatement(String accessToken){
        String url = buildUrl("/getStatement");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        QueryTransactionRequest queryTransactionRequest = QueryTransactionRequest
                .builder()
                .accessToken(accessToken)
                .build();

        HttpEntity<QueryTransactionRequest> queryTransactionRequestHttpEntity = new HttpEntity<>(queryTransactionRequest,headers);
        ResponseEntity<HTTPResult> queryTransactionResponseEntity = this.restTemplate.exchange(url, HttpMethod.POST,queryTransactionRequestHttpEntity,HTTPResult.class);

        Assertions.assertThat(queryTransactionResponseEntity).isNotNull();
        Assertions.assertThat(queryTransactionResponseEntity.getBody().getStatus()).isEqualTo(StatusCodeEnum.SUCCESS.getHttpStatusCode());

        QueryTransactionResult queryTransactionResult = (QueryTransactionResult) convertDataToPojo(queryTransactionResponseEntity.getBody().getData(),QueryTransactionResult.class);
        List<TransactionView> transactionViewList = queryTransactionResult.getTransactionList();
        TransactionView creditTransaction = transactionViewList.get(0);
        TransactionView debitTransaction = transactionViewList.get(1);

        Assertions.assertThat(creditTransaction.getTransactionValue()).isEqualTo(creditAmount);
        Assertions.assertThat(creditTransaction.getType()).isEqualTo(TransactionTypeEnum.CREDIT.getCode());

        Assertions.assertThat(debitTransaction.getTransactionValue()).isEqualTo(debitAmount);
        Assertions.assertThat(debitTransaction.getType()).isEqualTo(TransactionTypeEnum.DEBIT.getCode());

    }

    private void getAccount(String accessToken){
        String url = buildUrl("/getAccount");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        QueryAccountRequest queryAccountRequest = QueryAccountRequest
                .builder()
                .accessToken(accessToken)
                .build();

        HttpEntity<QueryAccountRequest> queryAccountRequestHttpEntity = new HttpEntity<>(queryAccountRequest,headers);
        ResponseEntity<HTTPResult> queryAccountResponseEntity = this.restTemplate.exchange(url, HttpMethod.POST,queryAccountRequestHttpEntity,HTTPResult.class);

        Assertions.assertThat(queryAccountResponseEntity).isNotNull();
        Assertions.assertThat(queryAccountResponseEntity.getBody().getStatus()).isEqualTo(StatusCodeEnum.SUCCESS.getHttpStatusCode());

        QueryAccountResult queryAccountResult = (QueryAccountResult) convertDataToPojo(queryAccountResponseEntity.getBody().getData(),QueryAccountResult.class);

        Long expectedRemainingBalance = AccountConstant.INITIAL_BALANCE - creditAmount + debitAmount;

        Assertions.assertThat(queryAccountResult.getBalance()).isEqualTo(expectedRemainingBalance);

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
