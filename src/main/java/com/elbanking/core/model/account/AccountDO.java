package com.elbanking.core.model.account;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "accounts")
public class AccountDO {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String id;
    private String user_id;
    private String balance_unit;
    private Long balance_value;
    private Date gmt_create;
    private Date gmt_modified;
}
