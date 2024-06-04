package com.elbanking.core.controller.user;

import com.elbanking.core.enums.CoreException;
import com.elbanking.core.enums.StatusCodeEnum;
import com.elbanking.core.manager.user.UserManager;
import com.elbanking.core.mapper.user.UserMapper;
import com.elbanking.core.model.HTTPResult;
import com.elbanking.core.model.ResultData;
import com.elbanking.core.model.user.RegisterUserRequest;
import com.elbanking.core.model.user.RegisterUserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserManager userManager;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/users")
    public ResponseEntity<HTTPResult> signUp(@RequestBody RegisterUserRequest registerUserRequest){
        ResultData resultData = null;
        StatusCodeEnum statusCode;

        try{
            RegisterUserResult registerUserResult = userManager.registerUser(registerUserRequest);
            resultData = registerUserResult;
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
