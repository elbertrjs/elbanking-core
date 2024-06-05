package com.elbanking.core.manager.transaction;

import com.elbanking.core.enums.CoreException;
import com.elbanking.core.enums.StatusCodeEnum;
import com.elbanking.core.enums.TransactionTypeEnum;
import com.elbanking.core.model.account.AccountDAO;
import com.elbanking.core.model.transaction.InsertTransactionRequest;
import com.elbanking.core.model.transaction.InsertTransactionResult;
import com.elbanking.core.service.account.AccountService;
import com.elbanking.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionManagerImpl implements TransactionManager{
    @Autowired
    private AccountService accountService;
    @Override
    public InsertTransactionResult insertTransaction(InsertTransactionRequest insertTransactionRequest) {
        if(IdUtil.isValidUUID(insertTransactionRequest.getAccountId()) == false){
            throw new CoreException(StatusCodeEnum.INVALID_ID);
        }

        AccountDAO queriedAccountDAO = accountService.queryAccountById(insertTransactionRequest.getAccountId());

        if(queriedAccountDAO == null){
            throw new CoreException(StatusCodeEnum.ACCOUNT_NOT_FOUND);
        }
        TransactionTypeEnum transactionType = TransactionTypeEnum.getByCode(insertTransactionRequest.getType());

        AccountDAO updatedAccountDAO = null;
        if(transactionType == TransactionTypeEnum.CREDIT){
            if(queriedAccountDAO.getBalanceValue() < insertTransactionRequest.getAmount()){
                throw new CoreException(StatusCodeEnum.BALANCE_INSUFFICIENT);
            }

            updatedAccountDAO = accountService.subtractBalance(insertTransactionRequest.getAccountId(),insertTransactionRequest.getAmount());
        }else if(transactionType == TransactionTypeEnum.DEBIT){

            updatedAccountDAO = accountService.addBalance(insertTransactionRequest.getAccountId(), insertTransactionRequest.getAmount());
        }else{
            throw new CoreException(StatusCodeEnum.INVALID_TRANSACTION_TYPE);
        }



        return InsertTransactionResult.builder()
                .remainingBalance(updatedAccountDAO.getBalanceValue())
                .build();
    }
}
