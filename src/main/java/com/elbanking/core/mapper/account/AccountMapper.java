package com.elbanking.core.mapper.account;

import com.elbanking.core.model.account.AccountDAO;
import com.elbanking.core.model.account.AccountDO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDO convertToAccountDO(AccountDAO accountDAO);

    AccountDAO convertToAccountDAO(AccountDO accountDO);

}
