package com.elbanking.core.repository;

import com.elbanking.core.model.user.UserDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserDO,String> {
    @Query(value = "SELECT * FROM users WHERE email = ?1", nativeQuery = true)
    UserDO findByEmail(String emailAddress);
}
