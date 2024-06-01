package com.elbanking.core.controller.user;

import com.elbanking.core.manager.user.UserManager;
import com.elbanking.core.mapper.user.UserMapper;
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

    @Autowired
    private UserMapper userMapper;
    @PostMapping("/users")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestForm signUpRequestForm){

        RegisterUserRequest registerUserRequest = userMapper.convertToRegisterUserRequest(signUpRequestForm);
        RegisterUserResult registerUserResult = userManager.registerUser(registerUserRequest);

        return
            ResponseEntity
                .status(HttpStatus.OK)
                .body(registerUserResult);
    }
}
