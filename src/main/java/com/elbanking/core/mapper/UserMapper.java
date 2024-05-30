package com.elbanking.core.mapper;

import com.elbanking.core.model.user.RegisterUserRequest;
import com.elbanking.core.model.user.SignUpRequestForm;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    RegisterUserRequest convertToRegisterUserRequest(SignUpRequestForm signUpRequestForm);
}
