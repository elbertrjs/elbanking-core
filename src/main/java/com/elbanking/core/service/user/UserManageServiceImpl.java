package com.elbanking.core.service.user;

import com.elbanking.core.mapper.user.UserMapper;
import com.elbanking.core.model.user.UserDAO;
import com.elbanking.core.model.user.UserDO;
import com.elbanking.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserManageServiceImpl implements UserManageService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDAO insertUser(UserDAO userDAO) {
        UserDO userDO = userMapper.convertToUserDO(userDAO);
        UserDO createdUserDO = (UserDO) userRepository.save(userDO);
        return userMapper.convertToUserDAO(createdUserDO);
    }
}
