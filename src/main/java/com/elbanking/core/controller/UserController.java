package com.elbanking.core.controller;

import com.elbanking.core.model.user.SignUpRequestForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @PostMapping("/users")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestForm signUpRequestForm){

        return
            ResponseEntity
                .status(HttpStatus.OK)
                .body("Success");
    }
}
