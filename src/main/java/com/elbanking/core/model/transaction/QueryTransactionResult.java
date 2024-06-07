package com.elbanking.core.model.transaction;

import com.elbanking.core.model.ResultData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryTransactionResult extends ResultData {
    private String accountId;
    private List<TransactionView> transactionList;
}
