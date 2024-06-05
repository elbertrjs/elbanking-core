package com.elbanking.core.manager.transaction;

import com.elbanking.core.model.transaction.InsertTransactionRequest;
import com.elbanking.core.model.transaction.InsertTransactionResult;
import com.elbanking.core.model.transaction.QueryTransactionRequest;
import com.elbanking.core.model.transaction.QueryTransactionResult;

public interface TransactionManager {
    InsertTransactionResult insertTransaction(InsertTransactionRequest insertTransactionRequest);

    QueryTransactionResult queryTransaction(QueryTransactionRequest queryTransactionRequest);
}
