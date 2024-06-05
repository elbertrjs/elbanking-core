package com.elbanking.core.enums;

import com.elbanking.core.model.HTTPResult;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum StatusCodeEnum {

    SUCCESS(HttpStatus.OK.value(),"00000","Success"),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST.value(), "00001","Invalid email format"),
    USER_EXISTS(HttpStatus.CONFLICT.value(), "00002","User already exist"),
    BALANCE_INSUFFICIENT(HttpStatus.UNPROCESSABLE_ENTITY.value(), "00003","Balance insufficient"),
    INVALID_TRANSACTION_TYPE(HttpStatus.BAD_REQUEST.value(),"00004","Invalid transaction type");

    private Integer httpStatusCode;
    private String internalStatusCode;
    private String message;

    StatusCodeEnum(Integer httpStatusCode,String internalStatusCode,String message){
        this.httpStatusCode = httpStatusCode;
        this.internalStatusCode = internalStatusCode;
        this.message = message;
    }


}
