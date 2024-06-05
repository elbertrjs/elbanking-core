package com.elbanking.core.model.account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "accounts")
public class AccountDO {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name = "account_id")
    private UUID accountId;
    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "balance_value")
    private Long balanceValue;
    @Column(name = "balance_currency")
    private String balanceCurrency;
    @Column(name = "gmt_create")
    private Date gmtCreate;
    @Column(name = "gmt_modified")
    private Date gmtModified;
}
