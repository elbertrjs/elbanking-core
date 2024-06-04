package com.elbanking.core.manager.transaction;

import com.elbanking.core.model.transaction.InsertTransactionRequest;
import com.elbanking.core.model.transaction.InsertTransactionResult;

public interface TransactionManager {
    InsertTransactionResult insertTransaction(InsertTransactionRequest insertTransactionRequest);
}
