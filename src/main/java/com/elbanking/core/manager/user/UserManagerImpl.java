package com.elbanking.core.manager.user;

import com.elbanking.core.enums.StatusCodeEnum;
import com.elbanking.core.mapper.user.UserMapper;
import com.elbanking.core.model.account.AccountDAO;
import com.elbanking.core.model.user.RegisterUserRequest;
import com.elbanking.core.model.user.RegisterUserResult;
import com.elbanking.core.model.user.RegisterUserView;
import com.elbanking.core.model.user.UserDAO;
import com.elbanking.core.service.account.AccountManageService;
import com.elbanking.core.service.user.UserManageService;
import com.elbanking.core.util.EmailUtil;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@Service
public class UserManagerImpl implements UserManager{
    @Autowired
    private UserManageService userManageService;

    @Autowired
    private AccountManageService accountManageService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        UserDAO existingUser = userManageService.queryUser(registerUserRequest.getEmail());

        try{
            Assert.isNull(existingUser, StatusCodeEnum.INVALID_EMAIL_FORMAT.getMessage());
        }catch(IllegalArgumentException e){
            return RegisterUserResult
                    .builder()
                    .statusCode(StatusCodeEnum.USER_EXISTS)
                    .build();
        }
        UserDAO userToRegister = UserDAO.builder()
                .email(registerUserRequest.getEmail())
                .password(passwordEncoder.encode(registerUserRequest.getPassword()))
                .build();

        UserDAO createdUser = userManageService.insertUser(userToRegister);

        Money initialBalance = Money.of(new BigDecimal(500000),"IDR");

        AccountDAO accountDAO = AccountDAO
                .builder()
                .userId(createdUser.getId())
                .balance(initialBalance)
                .build();

        accountManageService.insertAccount(accountDAO);

        RegisterUserView registerUserView = userMapper.convertToRegisterUserView(createdUser);

        return RegisterUserResult.builder()
                .statusCode(StatusCodeEnum.SUCCESS)
                .data(registerUserView).build();

    }
}
