package com.elbanking.core.manager;

import com.elbanking.core.model.user.RegisterUserRequest;
import com.elbanking.core.model.user.RegisterUserResult;
import org.springframework.stereotype.Service;

@Service
public class UserManagerImpl implements UserManager{
    @Override
    public RegisterUserResult registerUser(RegisterUserRequest registerUserRequest) {
        return null;
    }
}
