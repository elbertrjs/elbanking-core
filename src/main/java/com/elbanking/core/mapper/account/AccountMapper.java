package com.elbanking.core.mapper.account;

import com.elbanking.core.model.account.AccountDAO;
import com.elbanking.core.model.account.AccountDO;
import org.javamoney.moneta.Money;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import javax.money.Monetary;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(source = "accountDAO.balance", target = "balanceValue", qualifiedByName = "getMoneyLongValue")
    @Mapping(source = "accountDAO.balance", target = "balanceCurrency", qualifiedByName = "getMoneyCurrency")
    AccountDO convertToAccountDO(AccountDAO accountDAO);

    @Mapping(source = "accountDO" , target = "balance", qualifiedByName = "assembleMoney")
    AccountDAO convertToAccountDAO(AccountDO accountDO);

    @Named("getMoneyLongValue")
    static Long getMoneyLongValue(Money money) {
        return Math.round(money.getNumber().doubleValueExact()*100);
    }

    @Named("getMoneyCurrency")
    static String getMoneyCurrency(Money money) {
        return money.getCurrency().getCurrencyCode();
    }

    @Named("assembleMoney")
    static Money assembleMoney(AccountDO accountDO) {
        return Money.ofMinor(Monetary.getCurrency(accountDO.getBalanceCurrency()),accountDO.getBalanceValue());
    }
}
