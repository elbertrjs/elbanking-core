package com.elbanking.core.repository;

import com.elbanking.core.model.user.UserDO;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository {
    @Query(value = "SELECT * FROM users WHERE email = ?1", nativeQuery = true)
    UserDO findByEmail(String emailAddress);
}
