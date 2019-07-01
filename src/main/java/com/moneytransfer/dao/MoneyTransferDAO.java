package com.moneytransfer.dao;

import com.moneytransfer.dto.Account;
import com.moneytransfer.dto.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface MoneyTransferDAO {
	long insertAccount(String alias, String type, BigDecimal balance, String currency);

	List<Account> fetchAllAccounts();

	Account findOneAccount(Integer id);

	boolean deposit(Integer id, BigDecimal amount);

	boolean withdraw(Integer id, BigDecimal amount);

	Transfer transfer(Integer fromAccount, Integer toAccount, BigDecimal amount, String currency);

	Transfer findOneTransfer(Integer transId);
}
