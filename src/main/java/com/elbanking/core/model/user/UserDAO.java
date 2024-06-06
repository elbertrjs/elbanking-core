package com.elbanking.core.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDAO {
    private String userId;
    private String email;
    private String roles;
    private String password;
    private Date gmtCreate;
    private Date gmtModified;
}
