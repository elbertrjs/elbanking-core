package com.elbanking.core.mapper.user;

import com.elbanking.core.model.user.RegisterUserResult;
import com.elbanking.core.model.user.UserDAO;
import com.elbanking.core.model.user.UserDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDO convertToUserDO(UserDAO userDAO);

    UserDAO convertToUserDAO(UserDO userDo);

    @Mapping(source = "id", target = "userId")
    RegisterUserResult convertToRegisterUserResult(UserDAO userDAO);
}
