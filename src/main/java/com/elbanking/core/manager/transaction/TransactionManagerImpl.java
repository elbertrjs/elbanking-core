package com.elbanking.core.manager.transaction;

import com.elbanking.core.enums.CoreException;
import com.elbanking.core.enums.StatusCodeEnum;
import com.elbanking.core.model.account.AccountDAO;
import com.elbanking.core.model.transaction.InsertTransactionRequest;
import com.elbanking.core.model.transaction.InsertTransactionResult;
import com.elbanking.core.service.account.AccountService;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionManagerImpl implements TransactionManager{
    @Autowired
    private AccountService accountService;
    @Override
    public InsertTransactionResult insertTransaction(InsertTransactionRequest insertTransactionRequest) {
        AccountDAO accountDAO = accountService.queryAccountById(insertTransactionRequest.getAccountId());

        Money transactionAmount = Money.of(insertTransactionRequest.getAmount(),"IDR");

        if(accountDAO.getBalance().isLessThan(transactionAmount)){
            throw new CoreException(StatusCodeEnum.BALANCE_INSUFFICIENT);
        }
        
        return null;
    }
}
