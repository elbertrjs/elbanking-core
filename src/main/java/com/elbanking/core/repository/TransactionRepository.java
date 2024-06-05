package com.elbanking.core.repository;

import com.elbanking.core.model.transaction.TransactionDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionDO, UUID> {

}
