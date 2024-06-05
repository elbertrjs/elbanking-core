package com.elbanking.core.manager.account;

import com.elbanking.core.model.account.QueryAccountRequest;
import com.elbanking.core.model.account.QueryAccountResult;

public interface AccountManager {
    QueryAccountResult queryAccount(QueryAccountRequest queryAccountRequest);
}
