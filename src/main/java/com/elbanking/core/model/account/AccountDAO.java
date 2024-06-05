package com.elbanking.core.model.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDAO {
    private String accountId;
    private String userId;
    /**
     * balanceValue stored in cent form
     * For example, IDR500000.12 will be stored as 50000012
     */
    private Long balanceValue;
    private String balanceCurrency;
    private Date gmtCreate;
    private Date gmtModified;
}
