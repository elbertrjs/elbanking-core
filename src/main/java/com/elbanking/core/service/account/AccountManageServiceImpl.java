package com.elbanking.core.service.account;

import com.elbanking.core.mapper.account.AccountMapper;
import com.elbanking.core.model.account.AccountDAO;
import com.elbanking.core.model.account.AccountDO;
import com.elbanking.core.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountManageServiceImpl implements AccountManageService {
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public AccountDAO insertAccount(AccountDAO accountDAO) {
        AccountDO userDO = accountMapper.convertToAccountDO(accountDAO);
        AccountDO accountDO = (AccountDO) accountRepository.save(userDO);
        return accountMapper.convertToAccountDAO(accountDO);
    }

    @Override
    public AccountDAO queryAccount(String id) {
        return null;
    }
}
