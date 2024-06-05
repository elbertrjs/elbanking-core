package com.elbanking.core.model.transaction;

import com.elbanking.core.model.ResultData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class InsertTransactionResult extends ResultData {
    private String transactionId;
    private Long transactionAmount;
    private Long remainingAccountBalance;
    private String currency;
}
