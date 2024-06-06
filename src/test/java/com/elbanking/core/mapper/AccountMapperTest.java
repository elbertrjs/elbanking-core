package com.elbanking.core.mapper;

import com.elbanking.core.constant.CurrencyCodeConstant;
import com.elbanking.core.constant.account.AccountConstant;
import com.elbanking.core.mapper.account.AccountMapper;
import com.elbanking.core.mapper.account.AccountMapperImpl;
import com.elbanking.core.model.account.AccountDAO;
import com.elbanking.core.model.account.AccountDO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
        String accountId = "80304ceb-b613-4748-ad87-8f6300e22764";
        String userId = "88d38f29-857f-4400-a643-8e2aacf3b5b0";
        Long balanceValue = 50000000L;
        String balanceCurrency = CurrencyCodeConstant.IDR;
        Date currentDate = new Date();
        AccountDAO accountDAO = AccountDAO
                .builder()
                .accountId(accountId)
                .userId(userId)
                .balanceValue(balanceValue)
                .balanceCurrency(balanceCurrency)
                .gmtCreate(currentDate)
                .gmtModified(currentDate)
                .build();

        AccountDO accountDO = accountMapper.convertToAccountDO(accountDAO);
        assertEquals(accountDAO,accountDO);
    }

    @Test
    public void testConvertToAccountDAO(){
        String accountId = "80304ceb-b613-4748-ad87-8f6300e22764";
        String userId = "88d38f29-857f-4400-a643-8e2aacf3b5b0";
        Long balanceValue = 50000000L;
        String balanceCurrency = CurrencyCodeConstant.IDR;
        Date currentDate = new Date();

        AccountDO accountDO = AccountDO
                .builder()
                .accountId(UUID.fromString(accountId))
                .userId(UUID.fromString(userId))
                .balanceValue(balanceValue)
                .balanceCurrency(balanceCurrency)
                .gmtCreate(currentDate)
                .gmtModified(currentDate)
                .build();

        AccountDAO accountDAO = accountMapper.convertToAccountDAO(accountDO);
        assertEquals(accountDAO,accountDO);
    }

    private void assertEquals(AccountDAO accountDAO, AccountDO accountDO){
        Assert.assertEquals(accountDAO.getAccountId(),accountDO.getAccountId().toString());
        Assert.assertEquals(accountDAO.getUserId(),accountDO.getUserId().toString());
        Assert.assertEquals(accountDAO.getBalanceValue(),accountDO.getBalanceValue());
        Assert.assertEquals(accountDAO.getBalanceCurrency(),accountDO.getBalanceCurrency());
        Assert.assertEquals(accountDAO.getGmtCreate(),accountDO.getGmtCreate());
        Assert.assertEquals(accountDAO.getGmtModified(),accountDO.getGmtModified());
    }
}
