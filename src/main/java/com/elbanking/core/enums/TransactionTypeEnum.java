package com.elbanking.core.enums;

import lombok.Getter;

@Getter
public enum TransactionTypeEnum {

    DEBIT("DEBIT"),
    CREDIT("CREDIT");

    private String code;

    TransactionTypeEnum(String code){
        this.code = code;
    }

    public static TransactionTypeEnum getByCode(String code){
        for(TransactionTypeEnum transactionTypeEnum : values()){
            if(transactionTypeEnum.getCode().equals(code)){
                return transactionTypeEnum;
            }
        }
        return null;
    }
}
