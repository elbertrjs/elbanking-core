package com.elbanking.core.model.transaction;

import com.elbanking.core.model.ResultData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class QueryTransactionResult extends ResultData {
    private String accountId;
    private List<TransactionView> transactionList;
}
