package com.elbanking.core.repository;


import com.elbanking.core.model.account.AccountDAO;
import com.elbanking.core.model.account.AccountDO;
import com.elbanking.core.model.user.UserDO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends CrudRepository<AccountDO, UUID> {
    @Query(value = "SELECT * FROM accounts WHERE user_id = ?1", nativeQuery = true)
    AccountDO findByUserId(String userId);

    @Query
            (value =
                    """
                    UPDATE accounts
                    set balance_value = balance_value + ?2
                    where account_id = ?1
                    RETURNING *;
                    """
                    , nativeQuery = true)
    AccountDO addBalance(UUID accountId, Long amount);

    @Query
            (value =
                    """
                    UPDATE accounts
                    set balance_value = balance_value - ?2
                    where account_id = ?1
                    RETURNING *;
                    """
            , nativeQuery = true)
    AccountDO subtractBalance(UUID accountId, Long amount);
}
