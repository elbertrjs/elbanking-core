package com.elbanking.core.model.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDAO {
    private String id;
    private String userId;
    private Money balance;
    private Date gmtCreate;
    private Date gmtModified;
}
