package com.elbanking.core.service;

import com.elbanking.core.model.user.UserDAO;

public class UserManageServiceImpl implements UserManageService{
    @Override
    public UserDAO insertUser(UserDAO userDAO) {
        // call repository here
        return userDAO;
    }
}
