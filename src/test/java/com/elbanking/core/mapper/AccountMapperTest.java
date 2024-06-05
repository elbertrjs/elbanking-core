package com.elbanking.core.mapper;

import com.elbanking.core.mapper.account.AccountMapper;
import com.elbanking.core.mapper.account.AccountMapperImpl;
import com.elbanking.core.model.account.AccountDAO;
import com.elbanking.core.model.account.AccountDO;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@ExtendWith(SpringExtension.class) // JUnit 5
@ContextConfiguration(classes = {
        AccountMapperImpl.class
})
public class AccountMapperTest {

    @Autowired
    AccountMapper accountMapper;

    @Test
    public void testConvertToAccountDO(){
        Money balance = Money.of(new BigDecimal("123.123"),"IDR");
           AccountDAO accountDAO = AccountDAO
                   .builder()
                   .accountId(UUID.randomUUID().toString())
                   .userId(UUID.randomUUID().toString())
                   .balanceValue(50000000L)
                   .gmtCreate(new Date())
                   .gmtModified(new Date())
                   .build();

            AccountDO accountDO = accountMapper.convertToAccountDO(accountDAO);
            System.out.println(accountDO.toString());
    }

    @Test
    public void testConvertToAccountDAO(){
        AccountDO accountDO = AccountDO
                .builder()
                .accountId(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .balanceCurrency("IDR")
                .balanceValue(12312L)
                .gmtCreate(new Date())
                .gmtModified(new Date())
                .build();

        AccountDAO accountDAO = accountMapper.convertToAccountDAO(accountDO);
        System.out.println(accountDAO.toString());
    }
}
