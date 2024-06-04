package com.elbanking.core.manager.transaction;

import com.elbanking.core.model.account.AccountDAO;
import com.elbanking.core.model.transaction.InsertTransactionRequest;
import com.elbanking.core.model.transaction.InsertTransactionResult;
import com.elbanking.core.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionManagerImpl implements TransactionManager{
    @Autowired
    private AccountService accountService;
    @Override
    public InsertTransactionResult insertTransaction(InsertTransactionRequest insertTransactionRequest) {
        AccountDAO accountDAO = accountService.queryAccountById(insertTransactionRequest.getAccountId());

        return InsertTransactionResult
                .builder()
                .s;
    }
}
