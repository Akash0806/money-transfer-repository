package com.moneytransfer.exception;

import com.moneytransfer.util.ErrorEnum;

public class AccountNotFoundException extends MoneyTransferBaseException {
	public AccountNotFoundException(ErrorEnum errorEnum) {
		super(errorEnum.getMessage(), errorEnum.getCode(), errorEnum.getType());
	}
}
