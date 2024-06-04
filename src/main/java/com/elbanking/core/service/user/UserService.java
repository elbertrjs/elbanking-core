package com.elbanking.core.service.user;

import com.elbanking.core.model.user.UserDAO;

public interface UserService {
    public UserDAO insertUser(UserDAO userDAO);

    public UserDAO queryUser(String email);
}
