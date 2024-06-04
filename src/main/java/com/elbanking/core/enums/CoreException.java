package com.elbanking.core.enums;

import lombok.Getter;

/**
 * An exception class for elbanking core service
 */
@Getter
public class CoreException extends RuntimeException{
    private StatusCodeEnum statusCode;
    public CoreException(StatusCodeEnum statusCode){
        super(statusCode.getMessage());
        this.statusCode = statusCode;
    }
}
