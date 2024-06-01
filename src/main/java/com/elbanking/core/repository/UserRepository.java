package com.elbanking.core.repository;

import com.elbanking.core.model.user.UserDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserDO,String> {

}
