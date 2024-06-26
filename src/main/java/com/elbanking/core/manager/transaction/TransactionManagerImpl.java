package com.elbanking.core.manager.transaction;

import com.elbanking.core.enums.CoreException;
import com.elbanking.core.enums.StatusCodeEnum;
import com.elbanking.core.enums.TransactionTypeEnum;
import com.elbanking.core.mapper.transaction.TransactionMapper;
import com.elbanking.core.model.account.AccountDAO;
import com.elbanking.core.model.transaction.InsertTransactionRequest;
import com.elbanking.core.model.transaction.InsertTransactionResult;
import com.elbanking.core.model.transaction.QueryTransactionRequest;
import com.elbanking.core.model.transaction.QueryTransactionResult;
import com.elbanking.core.model.transaction.TransactionDAO;
import com.elbanking.core.model.transaction.TransactionView;
import com.elbanking.core.service.account.AccountService;
import com.elbanking.core.service.authentication.JwtService;
import com.elbanking.core.service.transaction.TransactionService;
import com.elbanking.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionManagerImpl implements TransactionManager{
    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private JwtService jwtService;
    @Override
    public InsertTransactionResult insertTransaction(InsertTransactionRequest insertTransactionRequest) {
        String userId = jwtService.extractUserId(insertTransactionRequest.getAccessToken());
        if(IdUtil.isValidUUID(userId) == false){
            throw new CoreException(StatusCodeEnum.INVALID_ID);
        }

        AccountDAO queriedAccountDAO = accountService.queryAccountByUserId(userId);

        if(queriedAccountDAO == null){
            throw new CoreException(StatusCodeEnum.ACCOUNT_NOT_FOUND);
        }
        TransactionTypeEnum transactionType = TransactionTypeEnum.getByCode(insertTransactionRequest.getType());

        AccountDAO updatedAccountDAO = null;
        if(transactionType == TransactionTypeEnum.CREDIT){
            if(queriedAccountDAO.getBalanceValue() < insertTransactionRequest.getAmount()){
                throw new CoreException(StatusCodeEnum.BALANCE_INSUFFICIENT);
            }

            updatedAccountDAO = accountService.subtractBalance(queriedAccountDAO.getAccountId(),insertTransactionRequest.getAmount());
        }else if(transactionType == TransactionTypeEnum.DEBIT){

            updatedAccountDAO = accountService.addBalance(queriedAccountDAO.getAccountId(), insertTransactionRequest.getAmount());
        }else{
            throw new CoreException(StatusCodeEnum.INVALID_TRANSACTION_TYPE);
        }

        TransactionDAO transactionDAO = TransactionDAO
                .builder()
                .accountId(updatedAccountDAO.getAccountId())
                .transactionValue(insertTransactionRequest.getAmount())
                .transactionCurrency(updatedAccountDAO.getBalanceCurrency())
                .type(insertTransactionRequest.getType())
                .note(insertTransactionRequest.getNote()).build();

        TransactionDAO insertedTransactionDAO = transactionService.insertTransaction(transactionDAO);
        return InsertTransactionResult.builder()
                .remainingAccountBalance(updatedAccountDAO.getBalanceValue())
                .transactionId(insertedTransactionDAO.getTransactionId())
                .transactionAmount(insertedTransactionDAO.getTransactionValue())
                .currency(insertedTransactionDAO.getTransactionCurrency())
                .build();
    }

    @Override
    public QueryTransactionResult queryTransaction(QueryTransactionRequest queryTransactionRequest) {
        String userId = jwtService.extractUserId(queryTransactionRequest.getAccessToken());
        if(IdUtil.isValidUUID(userId) == false){
            throw new CoreException(StatusCodeEnum.INVALID_ID);
        }

        AccountDAO queriedAccountDAO = accountService.queryAccountByUserId(userId);
        if(queriedAccountDAO == null){
            throw new CoreException(StatusCodeEnum.ACCOUNT_NOT_FOUND);
        }
        List<TransactionDAO> transactionDAOList = transactionService.queryTransactionsByAccountId(queriedAccountDAO.getAccountId());
        List<TransactionView> transactionViewlist = transactionMapper.convertToTransactionViewList(transactionDAOList);

        return QueryTransactionResult.builder()
                .accountId(queriedAccountDAO.getAccountId())
                .transactionList(transactionViewlist)
                .build();
    }
}
