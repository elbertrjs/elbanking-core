package com.elbanking.core.service.transaction;

import com.elbanking.core.model.account.AccountDAO;
import com.elbanking.core.model.transaction.TransactionDAO;

import java.util.List;

public interface TransactionService {
    TransactionDAO insertTransaction(TransactionDAO transactionDAO);

    List<TransactionDAO> queryTransactionsByAccountId(String accountId);
}
