package com.moneytransfer.services;

import com.moneytransfer.model.request.TransferRequest;
import com.moneytransfer.model.response.TransferResponse;

public interface TransferService {
	TransferResponse transfer(TransferRequest request);

	TransferResponse findTransferByTransId(Integer transId);
}
