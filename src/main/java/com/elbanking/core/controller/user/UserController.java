package com.elbanking.core.controller.user;

import com.elbanking.core.enums.CoreException;
import com.elbanking.core.enums.StatusCodeEnum;
import com.elbanking.core.manager.user.UserManager;
import com.elbanking.core.model.HTTPResult;
import com.elbanking.core.model.ResultData;
import com.elbanking.core.model.authentication.AuthRequest;
import com.elbanking.core.model.authentication.AuthResult;
import com.elbanking.core.model.user.RegisterUserRequest;
import com.elbanking.core.model.user.RegisterUserResult;
import com.elbanking.core.service.authentication.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserManager userManager;

    @PostMapping("/signUp")
    public ResponseEntity<HTTPResult> signUp(@RequestBody RegisterUserRequest registerUserRequest){
        ResultData resultData = null;
        StatusCodeEnum statusCode;

        try{
            resultData = userManager.registerUser(registerUserRequest);
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

    @PostMapping("/login")
    public ResponseEntity<HTTPResult> login(@RequestBody AuthRequest authRequest) {
        ResultData resultData = null;
        StatusCodeEnum statusCode;

        try{
            resultData = userManager.authenticate(authRequest);
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
