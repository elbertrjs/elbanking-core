package com.elbanking.core.service.transaction;

import com.elbanking.core.model.transaction.TransactionDAO;

public interface TransactionService {
    TransactionDAO insertTransaction(TransactionDAO transactionDAO);
}
