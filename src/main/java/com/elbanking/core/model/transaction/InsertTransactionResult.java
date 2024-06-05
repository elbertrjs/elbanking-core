package com.elbanking.core.model.transaction;

import com.elbanking.core.model.ResultData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@Builder
public class InsertTransactionResult extends ResultData {
    private Long remainingBalance;
}
