package com.elbanking.core.service.account;

import com.elbanking.core.model.account.AccountDAO;

public interface AccountService {
    AccountDAO insertAccount(AccountDAO accountDAO);

    AccountDAO queryAccountById(String accountId);

    AccountDAO addBalance(String accountId, Long amount);

    AccountDAO subtractBalance(String accountId, Long amount);
}
