package com.elbanking.core.model;

import com.elbanking.core.enums.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseManagerResult {
    private StatusCodeEnum statusCode;
    private ResultData data;
}
