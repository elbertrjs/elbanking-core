package com.elbanking.core.service.account;

import com.elbanking.core.mapper.account.AccountMapper;
import com.elbanking.core.model.account.AccountDAO;
import com.elbanking.core.model.account.AccountDO;
import com.elbanking.core.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public AccountDAO insertAccount(AccountDAO accountDAO) {
        accountDAO.setGmtCreate(new Date());
        accountDAO.setGmtModified(new Date());

        AccountDO accountDO = accountMapper.convertToAccountDO(accountDAO);
        AccountDO createdAccountDO = accountRepository.save(accountDO);
        return accountMapper.convertToAccountDAO(createdAccountDO);
    }

    @Override
    public AccountDAO queryAccount(String id) {
        return null;
    }
}
