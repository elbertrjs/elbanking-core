package com.elbanking.core.service.user;

import com.elbanking.core.mapper.user.UserMapper;
import com.elbanking.core.model.user.UserDAO;
import com.elbanking.core.model.user.UserDO;
import com.elbanking.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserManageServiceImpl implements UserManageService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDAO insertUser(UserDAO userDAO) {
        userDAO.setGmtCreate(new Date());
        userDAO.setGmtModified(new Date());
        UserDO userDO = userMapper.convertToUserDO(userDAO);
        UserDO createdUserDO = userRepository.save(userDO);
        return userMapper.convertToUserDAO(createdUserDO);
    }

    @Override
    public UserDAO queryUser(String email) {
        UserDO userDO = userRepository.findByEmail(email);
        UserDAO userDAO = userMapper.convertToUserDAO(userDO);
        return userDAO;
    }
}
