package com.elbanking.core.service.user;

import com.elbanking.core.enums.CoreException;
import com.elbanking.core.mapper.user.UserMapper;
import com.elbanking.core.model.user.UserDAO;
import com.elbanking.core.model.user.UserDO;
import com.elbanking.core.repository.UserRepository;
import com.elbanking.core.service.authentication.UserInfoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService , UserDetailsService {

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
    public UserDAO queryUserByEmail(String email) {
        UserDO userDO = userRepository.findByEmail(email);
        UserDAO userDAO = userMapper.convertToUserDAO(userDO);
        return userDAO;
    }

    @Override
    public UserDAO queryUserByUserId(String userId) {
        UUID userUUID = UUID.fromString(userId);
        Optional<UserDO> queriedUserDO = userRepository.findById(userUUID).stream().findFirst();
        if(queriedUserDO.isPresent() == false){
            return null;
        }
        UserDO userDO = queriedUserDO.get();
        return userMapper.convertToUserDAO(userDO);
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws CoreException {
        UserDAO userDAO = queryUserByUserId(userId);
        if(userDAO == null){
            throw new UsernameNotFoundException("User not found");
        }
        return new UserInfoDetails(userDAO);
    }
}
