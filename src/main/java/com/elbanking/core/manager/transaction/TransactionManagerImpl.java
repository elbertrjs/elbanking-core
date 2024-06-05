package com.elbanking.core.manager.transaction;

import com.elbanking.core.enums.CoreException;
import com.elbanking.core.enums.StatusCodeEnum;
import com.elbanking.core.enums.TransactionTypeEnum;
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
        AccountDAO queriedAccountDAO = accountService.queryAccountById(insertTransactionRequest.getAccountId());

        TransactionTypeEnum transactionType = TransactionTypeEnum.getByCode(insertTransactionRequest.getType());



        Money transactionAmount = Money.of(insertTransactionRequest.getAmount(),"IDR");
        Long transactionAmountCent = Math.round(transactionAmount.getNumber().doubleValueExact()*100);

        AccountDAO updatedAccountDAO = null;
        if(transactionType == TransactionTypeEnum.CREDIT){
            if(queriedAccountDAO.getBalance().isLessThan(transactionAmount)){
                throw new CoreException(StatusCodeEnum.BALANCE_INSUFFICIENT);
            }

            updatedAccountDAO = accountService.subtractBalance(insertTransactionRequest.getAccountId(),transactionAmountCent);
        }else if(transactionType == TransactionTypeEnum.DEBIT){

            updatedAccountDAO = accountService.addBalance(insertTransactionRequest.getAccountId(), transactionAmountCent);
        }else{
            throw new CoreException(StatusCodeEnum.INVALID_TRANSACTION_TYPE);
        }

        return InsertTransactionResult.builder()
                .remainingBalance(Math.round(updatedAccountDAO.getBalance().getNumber().doubleValueExact()*100))
                .build();
    }
}
