package com.elbanking.core.controller.TransactionController;

import com.elbanking.core.enums.CoreException;
import com.elbanking.core.enums.StatusCodeEnum;
import com.elbanking.core.manager.transaction.TransactionManager;
import com.elbanking.core.manager.user.UserManager;
import com.elbanking.core.model.HTTPResult;
import com.elbanking.core.model.ResultData;
import com.elbanking.core.model.transaction.InsertTransactionRequest;
import com.elbanking.core.model.transaction.InsertTransactionResult;
import com.elbanking.core.model.user.RegisterUserRequest;
import com.elbanking.core.model.user.RegisterUserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    private TransactionManager transactionManager;

    @PostMapping("/transactions")
    public ResponseEntity<HTTPResult> signUp(@RequestBody InsertTransactionRequest insertTransactionRequest){
        ResultData resultData = null;
        StatusCodeEnum statusCode;

        try{
            InsertTransactionResult insertTransactionResult = transactionManager.insertTransaction(insertTransactionRequest);
            resultData = insertTransactionResult;
            statusCode = StatusCodeEnum.SUCCESS;
        }catch(CoreException e){
            statusCode = e.getStatusCode();
        }
        HTTPResult httpResult = HTTPResult
                .builder()
                .status(statusCode.getHttpStatusCode())
                .message(statusCode.getMessage())
                .data(resultData).build();
        return ResponseEntity
                .status(statusCode.getHttpStatusCode())
                .body(httpResult);
    }
}