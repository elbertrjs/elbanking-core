package com.elbanking.core.service.account;

import com.elbanking.core.mapper.account.AccountMapper;
import com.elbanking.core.model.account.AccountDAO;
import com.elbanking.core.model.account.AccountDO;
import com.elbanking.core.repository.AccountRepository;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

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
    public AccountDAO queryAccountById(String id) {
        Optional<AccountDO> queriedAccountDO = accountRepository.findById(id).stream().findFirst();
        if(queriedAccountDO.isPresent() == false){
            return null;
        }
        AccountDO accountDO = queriedAccountDO.get();
        return accountMapper.convertToAccountDAO(accountDO);
    }

    @Override
    public AccountDAO addBalance(String accountId, Long amount) {
        AccountDO accountDO = accountRepository.addBalance(accountId,amount);
        return accountMapper.convertToAccountDAO(accountDO);
    }

    @Override
    public AccountDAO subtractBalance(String accountId, Long amount) {
        AccountDO accountDO = accountRepository.subtractBalance(accountId,amount);
        return accountMapper.convertToAccountDAO(accountDO);
    }
}
