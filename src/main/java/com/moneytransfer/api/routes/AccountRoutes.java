package com.moneytransfer.api.routes;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.moneytransfer.constants.MoneyTransferConstant;
import com.moneytransfer.exception.handler.JsonTransformer;
import com.moneytransfer.model.request.AccountRequest;
import com.moneytransfer.services.AccountService;
import org.eclipse.jetty.http.HttpStatus;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * This class serves all account related resources such as add new account,
 * fetch existing account details, deposit and withdraw like functionality.
 *
 *
 */
public class AccountRoutes {

	@Inject
	private AccountService accountService;

	public AccountRoutes() {
		initializeAccountRoutes();
	}

	public void initializeAccountRoutes() {

		post(MoneyTransferConstant.V_1_ACCOUNT + "/add", (request, response) -> {
			response.status(HttpStatus.CREATED_201);

			AccountRequest account = new Gson().fromJson(request.body(), AccountRequest.class);
			return accountService.addAccount(account);
		}, JsonTransformer::toJson);

		get(MoneyTransferConstant.V_1_ACCOUNT + "/all", (request, response) -> accountService.fetchAllAccounts(),
				JsonTransformer::toJson);

		get(MoneyTransferConstant.V_1_ACCOUNT + "/:id", ((request, response) ->
						accountService.findOneAccount(request.params(":id"))),
				JsonTransformer::toJson);

		post(MoneyTransferConstant.V_1_ACCOUNT + "/deposit", (request, response) -> {
			AccountRequest account = new Gson().fromJson(request.body(), AccountRequest.class);
			return accountService.deposit(account);
		}, JsonTransformer::toJson);

		post(MoneyTransferConstant.V_1_ACCOUNT + "/withdraw", (request, response) -> {
			AccountRequest account = new Gson().fromJson(request.body(), AccountRequest.class);
			return accountService.withdraw(account);
		},JsonTransformer::toJson);


	}
}
