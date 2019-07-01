package com.moneytransfer.services;

import com.moneytransfer.model.request.AccountRequest;
import com.moneytransfer.model.response.AccountResponse;
import com.moneytransfer.model.response.AccountResponseList;

public interface AccountService {
	AccountResponse addAccount(AccountRequest account);

	AccountResponseList fetchAllAccounts();

	AccountResponse findOneAccount(String accountNumber);

	AccountResponse deposit(AccountRequest account);

	AccountResponse withdraw(AccountRequest account);


}
