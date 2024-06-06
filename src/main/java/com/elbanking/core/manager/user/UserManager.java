package com.elbanking.core.manager.user;

import com.elbanking.core.model.authentication.AuthRequest;
import com.elbanking.core.model.user.RegisterUserRequest;
import com.elbanking.core.model.user.RegisterUserResult;

public interface UserManager {
    RegisterUserResult registerUser(RegisterUserRequest registerUserRequest);

    String authenticate(AuthRequest authRequest);
}
