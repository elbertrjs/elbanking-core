package com.elbanking.core.manager.user;

import com.elbanking.core.constant.account.AccountConstant;
import com.elbanking.core.enums.CoreException;
import com.elbanking.core.enums.RoleConstant;
import com.elbanking.core.enums.StatusCodeEnum;
import com.elbanking.core.model.account.AccountDAO;
import com.elbanking.core.model.authentication.AuthRequest;
import com.elbanking.core.model.user.RegisterUserRequest;
import com.elbanking.core.model.user.RegisterUserResult;
import com.elbanking.core.model.user.UserDAO;
import com.elbanking.core.service.account.AccountService;
import com.elbanking.core.service.authentication.JwtService;
import com.elbanking.core.service.user.UserService;
import com.elbanking.core.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserManagerImpl implements UserManager{
    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Override
    public RegisterUserResult registerUser(RegisterUserRequest registerUserRequest) {
        boolean isEmailValid = EmailUtil.isEmailValid(registerUserRequest.getEmail());

        if(isEmailValid == false){
            throw new CoreException(StatusCodeEnum.INVALID_EMAIL_FORMAT);
        }

        UserDAO existingUser = userService.queryUserByEmail(registerUserRequest.getEmail());

        if(existingUser != null){
            throw new CoreException(StatusCodeEnum.USER_EXISTS);
        }

        UserDAO userToRegister = UserDAO.builder()
                .email(registerUserRequest.getEmail())
                .password(passwordEncoder.encode(registerUserRequest.getPassword()))
                .roles(RoleConstant.ROLE_USER)
                .build();

        UserDAO createdUser = userService.insertUser(userToRegister);

        AccountDAO accountDAO = AccountDAO
                .builder()
                .userId(createdUser.getUserId())
                .balanceValue(AccountConstant.INITIAL_BALANCE)
                .balanceCurrency(AccountConstant.DEFAULT_CURRENCY)
                .build();

        AccountDAO createdAccountDAO = accountService.insertAccount(accountDAO);

        String accessToken = authenticate(
                AuthRequest.builder()
                        .email(registerUserRequest.getEmail())
                        .password(registerUserRequest.getPassword())
                        .build()
        );
        return RegisterUserResult.builder()
                .accessToken(accessToken)
                .initialBalance(createdAccountDAO.getBalanceValue())
                .accountCurrency(createdAccountDAO.getBalanceCurrency())
                .build();

    }

    @Override
    public String authenticate(AuthRequest authRequest) {
        UserDAO queriedUser = userService.queryUserByEmail(authRequest.getEmail());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(queriedUser.getUserId(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(queriedUser.getUserId());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
}
