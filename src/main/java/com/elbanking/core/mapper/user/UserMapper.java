package com.elbanking.core.mapper.user;

import com.elbanking.core.model.user.UserDAO;
import com.elbanking.core.model.user.UserDO;
import com.elbanking.core.service.authentication.UserInfoDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDO convertToUserDO(UserDAO userDAO);

    UserDAO convertToUserDAO(UserDO userDo);

    UserInfoDetails convertToUserInfoDetails(UserDAO userDAO);

}
