package com.elbanking.core.model.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsertTransactionRequest {
    private String idempotencyKey;
    private String accessToken;
    private Long amount;
    private String type;
    private String note;
}
