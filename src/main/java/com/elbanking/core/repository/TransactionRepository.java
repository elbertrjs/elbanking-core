package com.elbanking.core.repository;

import com.elbanking.core.model.account.AccountDO;
import com.elbanking.core.model.transaction.TransactionDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionDO, UUID> {
    @Query(value = "SELECT * FROM transactions WHERE account_id = ?1", nativeQuery = true)
    List<TransactionDO> findAllByAccountId(UUID accountId);
}
