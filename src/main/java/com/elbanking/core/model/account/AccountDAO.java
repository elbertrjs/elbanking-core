package com.elbanking.core.model.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDAO {
    private String id;
    private String user_id;
    private CurrencyUnit balance_unit;
    private MonetaryAmount balance_value;
    private Date gmt_create;
    private Date gmt_modified;
}
