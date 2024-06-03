package com.elbanking.core.mapper.user;

import com.elbanking.core.model.user.RegisterUserRequest;
import com.elbanking.core.model.user.RegisterUserResult;
import com.elbanking.core.model.user.RegisterUserView;
import com.elbanking.core.model.user.SignUpRequestForm;
import com.elbanking.core.model.user.UserDAO;
import com.elbanking.core.model.user.UserDO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    RegisterUserRequest convertToRegisterUserRequest(SignUpRequestForm signUpRequestForm);

    UserDO convertToUserDO(UserDAO userDAO);

    UserDAO convertToUserDAO(UserDO userDo);

    RegisterUserView convertToRegisterUserView(UserDAO userDAO);
}
