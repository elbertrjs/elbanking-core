package com.elbanking.core.service.account;

import com.elbanking.core.model.account.AccountDAO;

public interface AccountService {
    public AccountDAO insertAccount(AccountDAO accountDAO);

    public AccountDAO queryAccount(String id);
}
