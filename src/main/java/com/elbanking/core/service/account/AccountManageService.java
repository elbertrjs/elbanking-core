package com.elbanking.core.service.account;

import com.elbanking.core.model.account.AccountDAO;

public interface AccountManageService {
    public AccountDAO insertAccount(AccountDAO accountDAO);

    public AccountDAO queryAccount(String id);
}
