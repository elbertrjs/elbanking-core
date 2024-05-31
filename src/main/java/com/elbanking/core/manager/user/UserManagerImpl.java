package com.elbanking.core.manager.user;

import com.elbanking.core.mapper.user.UserMapper;
import com.elbanking.core.model.user.RegisterUserRequest;
import com.elbanking.core.model.user.RegisterUserResult;
import com.elbanking.core.model.user.UserDAO;
import com.elbanking.core.service.user.UserManageService;
import com.elbanking.core.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManagerImpl implements UserManager{
    @Autowired
    private UserManageService userManageService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public RegisterUserResult registerUser(RegisterUserRequest registerUserRequest) {
        boolean isEmailValid = EmailUtil.isEmailValid(registerUserRequest.getEmail());
        if(isEmailValid == false){
            // throw error here
        }
        UserDAO userToRegister = userMapper.convertToUserDAO(registerUserRequest);

        UserDAO createdUser = userManageService.insertUser(userToRegister);

        return userMapper.convertToUserRegisterResult(createdUser);

    }
}
