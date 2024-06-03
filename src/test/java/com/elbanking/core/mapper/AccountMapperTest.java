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
                   .id("1")
                   .userId("1")
                   .balance(balance)
                   .gmtCreate(new Date())
                   .gmtModified(new Date())
                   .build();

            AccountDO accountDO = accountMapper.convertToAccountDO(accountDAO);
            System.out.println(accountDO.toString());
    }
}
