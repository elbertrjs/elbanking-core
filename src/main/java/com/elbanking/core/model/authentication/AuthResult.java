package com.elbanking.core.model.authentication;

import com.elbanking.core.model.ResultData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResult extends ResultData {
    private String accessToken;
}
