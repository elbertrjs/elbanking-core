package com.elbanking.core.repository;


import com.elbanking.core.model.account.AccountDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends CrudRepository<AccountDO, UUID> {
    @Query(value = "SELECT * FROM accounts WHERE user_id = ?1", nativeQuery = true)
    Optional<AccountDO> findByUserId(UUID userId);

    @Query
            (value =
                    """
                    UPDATE accounts
                    set balance_value = balance_value + ?2,
                    gmt_modified = current_timestamp
                    where account_id = ?1
                    RETURNING *;
                    """
                    , nativeQuery = true)
    AccountDO addBalance(UUID accountId, Long amount);

    @Query
            (value =
                    """
                    UPDATE accounts
                    set balance_value = balance_value - ?2,
                    gmt_modified = current_timestamp
                    where account_id = ?1
                    RETURNING *;
                    """
            , nativeQuery = true)
    AccountDO subtractBalance(UUID accountId, Long amount);
}
