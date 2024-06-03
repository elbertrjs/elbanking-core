package com.elbanking.core.enums;

import lombok.Getter;

@Getter
public enum StatusCodeEnum {

    SUCCESS(200,"00000","Success"),
    INVALID_EMAIL_FORMAT(400,"00001","Invalid email format"),
    USER_EXISTS(409,"00002","User already exist");

    private Integer httpStatusCode;
    private String internalStatusCode;
    private String message;

    StatusCodeEnum(Integer httpStatusCode,String internalStatusCode,String message){
        this.httpStatusCode = httpStatusCode;
        this.internalStatusCode = internalStatusCode;
        this.message = message;
    }


}
