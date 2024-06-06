package com.elbanking.core.controller.account;


import com.elbanking.core.enums.CoreException;
import com.elbanking.core.enums.RoleConstant;
import com.elbanking.core.enums.StatusCodeEnum;
import com.elbanking.core.manager.account.AccountManager;
import com.elbanking.core.model.HTTPResult;
import com.elbanking.core.model.ResultData;
import com.elbanking.core.model.account.QueryAccountRequest;
import com.elbanking.core.model.account.QueryAccountResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    @Autowired
    private AccountManager accountManager;
    //uses POST method instead of GET to prevent sensitive data being exposed on URL
    @PostMapping("/getAccount")
    @PreAuthorize("hasAuthority('" + RoleConstant.ROLE_USER +"')")
    public ResponseEntity<HTTPResult> queryAccount(@RequestBody QueryAccountRequest queryAccountRequest) {
        ResultData resultData = null;
        StatusCodeEnum statusCode;

        try{
            QueryAccountResult queryAccountResult = accountManager.queryAccount(queryAccountRequest);
            resultData = queryAccountResult;
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
