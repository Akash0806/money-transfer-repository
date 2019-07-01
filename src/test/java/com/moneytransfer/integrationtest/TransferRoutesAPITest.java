package com.moneytransfer.integrationtest;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.moneytransfer.MoneyTransferMain;
import com.moneytransfer.model.error.APIError;
import com.moneytransfer.model.request.TransferRequest;
import com.moneytransfer.model.response.TransferResponse;
import com.moneytransfer.util.ErrorEnum;
import com.moneytransfer.util.StatusEnum;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test covers all transfer related functionalities between accounts
 * as integration by starting the light wight spark instance.
 */
public class TransferRoutesAPITest {
	@BeforeClass
	public static void beforeClass() throws Exception {
		MoneyTransferMain.main(null);
		Spark.awaitInitialization();
	}

	@Test
	public void verifySuccessfulTransfer() throws UnirestException {
		HttpResponse<String> response = Unirest.post("http://localhost:4567/v1/transfer/immediate")
				.header("accept", "application/json")
				.body(new Gson().toJson(mockTransferRequest()))
				.asString();
		TransferResponse tResponse = new Gson()
				.fromJson(response.getBody(), TransferResponse.class);

		assertThat(tResponse.getStatus()).isEqualTo(StatusEnum.Transfer.COMPLETED.name());
		assertThat(tResponse.getTransactionId()).isNotEmpty();
	}

	@Test
	public void verifyInvalidFromAmount() throws UnirestException {
		TransferRequest req = mockTransferRequest();
		req.setFromAccount("100090");
		HttpResponse<String> response = Unirest.post("http://localhost:4567/v1/transfer/immediate")
				.header("accept", "application/json")
				.body(new Gson().toJson(req))
				.asString();
		APIError apiError =
				new Gson().fromJson(response.getBody(), APIError.class);

		assertThat(apiError.getErrorCode()).isEqualTo(ErrorEnum.ACCOUNT_NOT_EXISTS.getCode());
	}

	@Test
	public void verifyInvalidAccountFormat() throws UnirestException {
		TransferRequest req = mockTransferRequest();
		req.setFromAccount("100abc");
		HttpResponse<String> response = Unirest.post("http://localhost:4567/v1/transfer/immediate")
				.header("accept", "application/json")
				.body(new Gson().toJson(req))
				.asString();
		APIError apiError =
				new Gson().fromJson(response.getBody(), APIError.class);

		assertThat(apiError.getErrorCode()).isEqualTo(ErrorEnum.INVALID_ACCOUNT_FORMAT.getCode());
	}

	@Test
	public void verifyTransferAmountIsNotNegative() throws UnirestException {
		TransferRequest req = mockTransferRequest();
		req.setAmount(new BigDecimal(-100));
		HttpResponse<String> response = Unirest.post("http://localhost:4567/v1/transfer/immediate")
				.header("accept", "application/json")
				.body(new Gson().toJson(req))
				.asString();
		APIError apiError =
				new Gson().fromJson(response.getBody(), APIError.class);

		assertThat(apiError.getErrorCode()).isEqualTo(ErrorEnum.TRANSFER_AMOUNT_ZERO_OR_NEGATIVE.getCode());
	}

	@Test
	public void verifyTransferAmountNotOverdrawn() throws UnirestException {
		TransferRequest req = mockTransferRequest();
		req.setAmount(new BigDecimal(1001));
		HttpResponse<String> response = Unirest.post("http://localhost:4567/v1/transfer/immediate")
				.header("accept", "application/json")
				.body(new Gson().toJson(req))
				.asString();
		APIError apiError =
				new Gson().fromJson(response.getBody(), APIError.class);

		assertThat(apiError.getType()).isEqualTo(ErrorEnum.AMOUNT_OVERDRAWN.getType());
	}

	@Test
	public void verifyFromAndToAccountIsDifferent() throws UnirestException {
		TransferRequest req = mockTransferRequest();
		req.setToAccount("100000");
		HttpResponse<String> response = Unirest.post("http://localhost:4567/v1/transfer/immediate")
				.header("accept", "application/json")
				.body(new Gson().toJson(req))
				.asString();
		APIError apiError =
				new Gson().fromJson(response.getBody(), APIError.class);

		assertThat(apiError.getErrorCode()).isEqualTo(ErrorEnum.FROM_TO_ACCOUNT_IS_SAME.getCode());
	}



	@AfterClass
	public static void afterClass() throws InterruptedException {
		Spark.stop();
		Thread.sleep(2000);
	}

	private TransferRequest mockTransferRequest() {
		return TransferRequest.builder()
				.fromAccount("100000")
				.toAccount("100001")
				.amount(BigDecimal.valueOf(100))
				.currency("USD")
				.build();
	}


}
