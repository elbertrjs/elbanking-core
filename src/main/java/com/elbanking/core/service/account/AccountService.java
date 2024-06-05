package com.elbanking.core.service.account;

import com.elbanking.core.model.account.AccountDAO;
import org.javamoney.moneta.Money;

public interface AccountService {
    AccountDAO insertAccount(AccountDAO accountDAO);

    AccountDAO queryAccountById(String id);

    AccountDAO addBalance(String accountId, Long amount);

    AccountDAO subtractBalance(String accountId, Long amount);
}
