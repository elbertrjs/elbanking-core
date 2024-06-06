package com.elbanking.core.controller.transaction;

import com.elbanking.core.enums.CoreException;
import com.elbanking.core.enums.RoleConstant;
import com.elbanking.core.enums.StatusCodeEnum;
import com.elbanking.core.manager.transaction.TransactionManager;
import com.elbanking.core.model.HTTPResult;
import com.elbanking.core.model.ResultData;
import com.elbanking.core.model.transaction.InsertTransactionRequest;
import com.elbanking.core.model.transaction.InsertTransactionResult;
import com.elbanking.core.model.transaction.QueryTransactionRequest;
import com.elbanking.core.model.transaction.QueryTransactionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    private TransactionManager transactionManager;

    @PostMapping("/transactions")
    @PreAuthorize("hasAuthority('" + RoleConstant.ROLE_USER +"')")
    public ResponseEntity<HTTPResult> submitTransaction(@RequestBody InsertTransactionRequest insertTransactionRequest){
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

    @PostMapping("/getStatement")
    @PreAuthorize("hasAuthority('" + RoleConstant.ROLE_USER +"')")
    public ResponseEntity<HTTPResult> queryStatement(@RequestBody QueryTransactionRequest queryTransactionRequest){
        ResultData resultData = null;
        StatusCodeEnum statusCode;

        try{
            QueryTransactionResult queryTransactionResult = transactionManager.queryTransaction(queryTransactionRequest);
            resultData = queryTransactionResult;
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
