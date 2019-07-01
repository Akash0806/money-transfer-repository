package com.moneytransfer.exception;

import com.moneytransfer.util.ErrorEnum;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AmountOverdrawnException extends MoneyTransferBaseException {
	public AmountOverdrawnException(ErrorEnum errorEnum) {
		super(errorEnum.getMessage(), errorEnum.getCode(), errorEnum.getType());
	}
}
