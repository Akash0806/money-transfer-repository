package com.moneytransfer.unittest.services;

import com.moneytransfer.dao.MoneyTransferDAO;
import com.moneytransfer.dto.Transfer;
import com.moneytransfer.model.Validator;
import com.moneytransfer.model.request.TransferRequest;
import com.moneytransfer.model.response.TransferResponse;
import com.moneytransfer.services.TransferServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransferServiceTest {
	@Mock
	private MoneyTransferDAO moneyTransferDAO;

	@Mock
	private Validator validator;

	@InjectMocks
	private TransferServiceImpl transferService;

	@Test
	public void testTransferTransfer() {
		when(moneyTransferDAO.transfer(10000,10001,
				new BigDecimal(100) ,"USD"))
				.thenReturn(mockTransferRes());

		TransferResponse response = transferService.transfer(mockTransferReq());

		assertThat(response.getTransactionId()).isEqualTo("12345678");
	}


	@Test
	public void fetchOneTransferTransaction() {
		when(moneyTransferDAO.findOneTransfer(100100))
				.thenReturn(mockTransferRes());
		TransferResponse response = transferService.findTransferByTransId(100100);

		assertThat(response.getTransactionId()).isEqualTo("12345678");
		assertThat(response.getStatus()).isEqualTo("SUCCESS");
	}



	private TransferRequest mockTransferReq() {
		return TransferRequest.builder()
				.fromAccount("10000")
				.toAccount("10001")
				.amount(new BigDecimal(100))
				.currency("USD")
				.build();
	}

	private Transfer mockTransferRes() {
		return Transfer.builder()
				.transId(12345678L)
				.status("SUCCESS")
				.transDate(LocalDateTime.now())
				.build();
	}



}
