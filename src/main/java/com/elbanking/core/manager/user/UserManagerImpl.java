package com.elbanking.core.manager.user;

import com.elbanking.core.enums.StatusCodeEnum;
import com.elbanking.core.mapper.user.UserMapper;
import com.elbanking.core.model.user.RegisterUserRequest;
import com.elbanking.core.model.user.RegisterUserResult;
import com.elbanking.core.model.user.RegisterUserView;
import com.elbanking.core.model.user.UserDAO;
import com.elbanking.core.service.user.UserManageService;
import com.elbanking.core.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserManagerImpl implements UserManager{
    @Autowired
    private UserManageService userManageService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public RegisterUserResult registerUser(RegisterUserRequest registerUserRequest) {
        boolean isEmailValid = EmailUtil.isEmailValid(registerUserRequest.getEmail());

        try{
            Assert.isTrue(isEmailValid, StatusCodeEnum.INVALID_EMAIL_FORMAT.getMessage());
        }catch(IllegalArgumentException e){

            return RegisterUserResult
                    .builder()
                    .statusCode(StatusCodeEnum.INVALID_EMAIL_FORMAT)
                    .build();
        }


        UserDAO userToRegister = userMapper.convertToUserDAO(registerUserRequest);

        UserDAO createdUser = userManageService.insertUser(userToRegister);

        RegisterUserView registerUserView = userMapper.convertToRegisterUserView(createdUser);

        return RegisterUserResult.builder()
                .statusCode(StatusCodeEnum.SUCCESS)
                .data(registerUserView).build();

    }
}
