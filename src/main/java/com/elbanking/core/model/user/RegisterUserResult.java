package com.elbanking.core.model.user;

import com.elbanking.core.model.ResultData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@Builder
public class RegisterUserResult extends ResultData {
    private String accessToken;
    private Long initialBalance;
    private String accountCurrency;
}
