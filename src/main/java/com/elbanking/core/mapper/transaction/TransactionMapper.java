package com.elbanking.core.mapper.transaction;

import com.elbanking.core.model.transaction.TransactionDAO;
import com.elbanking.core.model.transaction.TransactionDO;
import com.elbanking.core.model.transaction.TransactionView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionDO convertToTransactionDO(TransactionDAO transactionDAO);

    TransactionDAO convertToTransactionDAO(TransactionDO transactionDO);

    default List<TransactionDAO> convertToTransactionDAOList(List<TransactionDO> transactionDOList){
        return transactionDOList.stream()
                .map(
                        transactionDO -> convertToTransactionDAO(transactionDO)
                ).toList();
    }
    @Mapping(source = "gmtCreate",target = "transactionDate")
    TransactionView convertToTransactionView(TransactionDAO transactionDAO);

    default List<TransactionView> convertToTransactionViewList(List<TransactionDAO> transactionDAOList){
        return transactionDAOList.stream()
                .map(
                        transactionDAO -> convertToTransactionView(transactionDAO)
                ).toList();
    }
}
