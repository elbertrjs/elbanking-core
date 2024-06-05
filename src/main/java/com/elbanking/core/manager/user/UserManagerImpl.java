package com.elbanking.core.manager.user;

import com.elbanking.core.constant.account.AccountConstant;
import com.elbanking.core.enums.CoreException;
import com.elbanking.core.enums.StatusCodeEnum;
import com.elbanking.core.mapper.user.UserMapper;
import com.elbanking.core.model.account.AccountDAO;
import com.elbanking.core.model.user.RegisterUserRequest;
import com.elbanking.core.model.user.RegisterUserResult;
import com.elbanking.core.model.user.UserDAO;
import com.elbanking.core.service.account.AccountService;
import com.elbanking.core.service.user.UserService;
import com.elbanking.core.util.EmailUtil;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserManagerImpl implements UserManager{
    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public RegisterUserResult registerUser(RegisterUserRequest registerUserRequest) {
        boolean isEmailValid = EmailUtil.isEmailValid(registerUserRequest.getEmail());

        if(isEmailValid == false){
            throw new CoreException(StatusCodeEnum.INVALID_EMAIL_FORMAT);
        }

        UserDAO existingUser = userService.queryUser(registerUserRequest.getEmail());

        if(existingUser != null){
            throw new CoreException(StatusCodeEnum.USER_EXISTS);
        }

        UserDAO userToRegister = UserDAO.builder()
                .email(registerUserRequest.getEmail())
                .password(passwordEncoder.encode(registerUserRequest.getPassword()))
                .build();

        UserDAO createdUser = userService.insertUser(userToRegister);

        Money initialBalance = Money.of(new BigDecimal(500000),"IDR");

        AccountDAO accountDAO = AccountDAO
                .builder()
                .userId(createdUser.getId())
                .balanceValue(AccountConstant.INITIAL_BALANCE)
                .build();

        accountService.insertAccount(accountDAO);

        return userMapper.convertToRegisterUserResult(createdUser);

    }
}
