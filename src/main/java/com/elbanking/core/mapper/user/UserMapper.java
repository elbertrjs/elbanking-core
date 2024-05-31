package com.elbanking.core.mapper.user;

import com.elbanking.core.model.user.RegisterUserRequest;
import com.elbanking.core.model.user.SignUpRequestForm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    RegisterUserRequest convertToRegisterUserRequest(SignUpRequestForm signUpRequestForm);
}
