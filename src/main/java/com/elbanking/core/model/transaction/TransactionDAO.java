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
public class TransactionDAO {
    private String transactionId;
    private String accountId;
    private Long transaction_value;
    private String transaction_currency;
    private String type;
    private String note;
    private Date gmtCreate;
    private Date gmtModified;
}
