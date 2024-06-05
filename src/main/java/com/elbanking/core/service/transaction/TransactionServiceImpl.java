package com.elbanking.core.service.transaction;

import com.elbanking.core.mapper.transaction.TransactionMapper;
import com.elbanking.core.model.transaction.TransactionDAO;
import com.elbanking.core.model.transaction.TransactionDO;
import com.elbanking.core.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TransactionServiceImpl implements TransactionService{
    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public TransactionDAO insertTransaction(TransactionDAO transactionDAO) {
        transactionDAO.setGmtCreate(new Date());
        transactionDAO.setGmtModified(new Date());

        TransactionDO transactionDO = transactionMapper.convertToTransactionDO(transactionDAO);
        TransactionDO createdTransactioDO = transactionRepository.save(transactionDO);
        return transactionMapper.convertToTransactionDAO(createdTransactioDO);
    }
}
