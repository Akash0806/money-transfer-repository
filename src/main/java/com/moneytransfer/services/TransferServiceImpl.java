package com.moneytransfer.services;

import com.google.inject.Inject;
import com.moneytransfer.dao.MoneyTransferDAO;
import com.moneytransfer.dto.Transfer;
import com.moneytransfer.model.Validator;
import com.moneytransfer.model.request.TransferRequest;
import com.moneytransfer.model.response.TransferResponse;


public class TransferServiceImpl implements TransferService {

	private final MoneyTransferDAO moneyTransferDAO;
	private final Validator validator;


	@Inject
	public TransferServiceImpl(MoneyTransferDAO moneyTransferDAO, Validator validator) {
		this.moneyTransferDAO = moneyTransferDAO;
		this.validator = validator;
	}

	@Override
	public TransferResponse transfer(TransferRequest request) {
		validator.validateTransferRequest(request);
		Transfer transfer = moneyTransferDAO.transfer(Integer.valueOf(request.getFromAccount()),
				Integer.valueOf(request.getToAccount()), request.getAmount(),
				request.getCurrency());
		return mapObject(transfer);
	}



	@Override
	public TransferResponse findTransferByTransId(Integer transId) {
		Transfer transfer = moneyTransferDAO.findOneTransfer(transId);
		return mapObject(transfer);
	}

	private TransferResponse mapObject(Transfer transfer) {
		return TransferResponse.builder()
				.status(transfer.getStatus())
				.transactionId(String.valueOf(transfer.getTransId()))
				.transactionDate(transfer.getTransDate() != null? transfer.getTransDate().toString() : null)
				.scheduledDate(transfer.getScheduleDate() != null? transfer.getScheduleDate().toString() : null)
				.amount(transfer.getAmount())
				.currency(transfer.getCurrency())
				.fromAccount(transfer.getFromAcct() != null ? String.valueOf(transfer.getFromAcct()) : null )
				.toAccount(transfer.getToAcct() != null ? String.valueOf(transfer.getToAcct()) : null)
				.build();

	}
}
