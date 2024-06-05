package com.elbanking.core.service.account;

import com.elbanking.core.mapper.account.AccountMapper;
import com.elbanking.core.model.account.AccountDAO;
import com.elbanking.core.model.account.AccountDO;
import com.elbanking.core.repository.AccountRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountRepository accountRepository;

    // used for refreshing entity after update
    @Autowired
    private EntityManager entityManager;

    @Override
    public AccountDAO insertAccount(AccountDAO accountDAO) {
        accountDAO.setGmtCreate(new Date());
        accountDAO.setGmtModified(new Date());

        AccountDO accountDO = accountMapper.convertToAccountDO(accountDAO);
        AccountDO createdAccountDO = accountRepository.save(accountDO);
        return accountMapper.convertToAccountDAO(createdAccountDO);
    }

    @Override
    public AccountDAO queryAccountById(String accountId) {
        UUID accountUUID = UUID.fromString(accountId);
        Optional<AccountDO> queriedAccountDO = accountRepository.findById(accountUUID).stream().findFirst();
        if(queriedAccountDO.isPresent() == false){
            return null;
        }
        AccountDO accountDO = queriedAccountDO.get();
        return accountMapper.convertToAccountDAO(accountDO);
    }

    @Override
    @Transactional
    public AccountDAO addBalance(String accountId, Long amount) {
        UUID accountUUID = UUID.fromString(accountId);
        AccountDO accountDO = accountRepository.addBalance(accountUUID,amount);
        entityManager.refresh(accountDO);
        return accountMapper.convertToAccountDAO(accountDO);
    }

    @Override
    @Transactional
    public AccountDAO subtractBalance(String accountId, Long amount) {
        UUID accountUUID = UUID.fromString(accountId);
        AccountDO accountDO = accountRepository.subtractBalance(accountUUID,amount);
        entityManager.refresh(accountDO);
        return accountMapper.convertToAccountDAO(accountDO);
    }
}
