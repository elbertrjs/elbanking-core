package com.elbanking.core.controller;

import com.elbanking.core.manager.UserManager;
import com.elbanking.core.mapper.UserMapper;
import com.elbanking.core.model.user.RegisterUserRequest;
import com.elbanking.core.model.user.RegisterUserResult;
import com.elbanking.core.model.user.SignUpRequestForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserManager userManager;

    @Autowired UserMapper userMapper;
    @PostMapping("/users")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestForm signUpRequestForm){

        RegisterUserRequest registerUserRequest = userMapper.convertToRegisterUserRequest(signUpRequestForm);
        RegisterUserResult registerUserResult = userManager.registerUser(registerUserRequest);

        return
            ResponseEntity
                .status(HttpStatus.OK)
                .body("Success");
    }
}
