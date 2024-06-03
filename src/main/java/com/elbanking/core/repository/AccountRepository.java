package com.elbanking.core.repository;


import com.elbanking.core.model.account.AccountDO;
import com.elbanking.core.model.user.UserDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<AccountDO,String> {
    @Query(value = "SELECT * FROM accounts WHERE user_id = ?1", nativeQuery = true)
    UserDO findByUserId(String userId);
}
