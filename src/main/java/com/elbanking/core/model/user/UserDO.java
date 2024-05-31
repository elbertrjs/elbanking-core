package com.elbanking.core.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserDO {
    @Id
    private String id;
    private String email;
    private String password;
}
