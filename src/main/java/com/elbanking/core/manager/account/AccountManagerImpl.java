package com.elbanking.core.manager.account;

import com.elbanking.core.enums.CoreException;
import com.elbanking.core.enums.StatusCodeEnum;
import com.elbanking.core.model.account.AccountDAO;
import com.elbanking.core.model.account.QueryAccountRequest;
import com.elbanking.core.model.account.QueryAccountResult;
import com.elbanking.core.service.account.AccountService;
import com.elbanking.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountManagerImpl implements AccountManager{
    @Autowired
    private AccountService accountService;
    @Override
    public QueryAccountResult queryAccount(QueryAccountRequest queryAccountRequest) {
        if(IdUtil.isValidUUID(queryAccountRequest.getAccountId()) == false){
            throw new CoreException(StatusCodeEnum.INVALID_ID);
        }

        AccountDAO queriedAccount = accountService.queryAccountById(queryAccountRequest.getAccountId());
        if(queriedAccount == null){
            throw new CoreException(StatusCodeEnum.ACCOUNT_NOT_FOUND);
        }
        return QueryAccountResult.builder()
                .balance(queriedAccount.getBalanceValue())
                .currency(queriedAccount.getBalanceCurrency())
                .build();
    }
}
