package com.elbanking.core.model.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionView {
    private String transactionId;
    private Long transactionValue;
    private String transactionCurrency;
    private String type;
    private String note;
    private Date transactionDate;
}
