package com.elbanking.core.mapper.account;

import com.elbanking.core.model.account.AccountDAO;
import com.elbanking.core.model.account.AccountDO;
import org.javamoney.moneta.Money;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(source = "accountDAO.userId", target = "user_id")
    @Mapping(source = "accountDAO.balance", target = "balance_value", qualifiedByName = "getMoneyLongValue")
    @Mapping(source = "accountDAO.balance", target = "balance_currency", qualifiedByName = "getMoneyCurrency")
    @Mapping(source = "accountDAO.gmtCreate", target = "gmt_create")
    @Mapping(source = "accountDAO.gmtModified", target = "gmt_modified")
    AccountDO convertToAccountDO(AccountDAO accountDAO);

    AccountDAO convertToAccountDAO(AccountDO accountDO);

    @Named("getMoneyLongValue")
    static Long getMoneyLongValue(Money money) {
        return Math.round(money.getNumber().doubleValueExact()*100);
    }

    @Named("getMoneyCurrency")
    static String getMoneyCurrency(Money money) {
        return money.getCurrency().getCurrencyCode();
    }
}
