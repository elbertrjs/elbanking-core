package com.elbanking.core.mapper.transaction;

import com.elbanking.core.model.transaction.TransactionDAO;
import com.elbanking.core.model.transaction.TransactionDO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionDO convertToTransactionDO(TransactionDAO transactionDAO);

    TransactionDAO convertToTransactionDAO(TransactionDO transactionDO);
}
