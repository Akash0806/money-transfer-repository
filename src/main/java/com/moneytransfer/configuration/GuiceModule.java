package com.moneytransfer.configuration;

import com.google.inject.AbstractModule;
import com.moneytransfer.dao.MoneyTransferDAO;
import com.moneytransfer.dao.MoneyTransferDAOImpl;
import com.moneytransfer.model.Validator;
import com.moneytransfer.services.AccountService;
import com.moneytransfer.services.AccountServiceImpl;
import com.moneytransfer.services.TransferService;
import com.moneytransfer.services.TransferServiceImpl;

public class GuiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(AccountService.class).to(AccountServiceImpl.class);
		bind(TransferService.class).to(TransferServiceImpl.class);
		bind(MoneyTransferDAO.class).to(MoneyTransferDAOImpl.class);
		bind(Validator.class);
	}
}
