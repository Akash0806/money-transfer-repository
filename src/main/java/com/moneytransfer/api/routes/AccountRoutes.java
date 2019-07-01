package com.moneytransfer.api.routes;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.moneytransfer.constants.MoneyTransferConstant;
import com.moneytransfer.exception.handler.JsonTransformer;
import com.moneytransfer.model.error.APIError;
import com.moneytransfer.model.request.AccountRequest;
import com.moneytransfer.model.response.AccountResponse;
import com.moneytransfer.model.response.TransferResponse;
import com.moneytransfer.services.AccountService;
import io.swagger.annotations.*;
import org.eclipse.jetty.http.HttpStatus;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * This class serves all account related resources such as add new account,
 * fetch existing account details, deposit and withdraw like functionality.
 *
 *
 */
@Api
@Path(MoneyTransferConstant.V_1_ACCOUNT)
@Produces("application/json")
public class AccountRoutes {

	@Inject
	private AccountService accountService;

	public AccountRoutes() {
		initializeAccountRoutes();
	}
	@POST
	@ApiOperation(value = "Add Account", nickname="Create Account")
	@ApiImplicitParams({ //
			@ApiImplicitParam(required = true, dataType = "com.moneytransfer.model.request.AccountRequest", paramType = "body") //
	}) //
	@ApiResponses(value = { //
			@ApiResponse(code = 201, message = "Success", response= AccountResponse.class), //
			@ApiResponse(code = 400, message = "Account balance can't be negative at open", response= APIError.class), //
			@ApiResponse(code = 400, message = "Account type should be Checking or Savings", response=APIError.class), //
			@ApiResponse(code = 400, message = "BAD Request", response=APIError.class), //
	    	@ApiResponse(code = 500, message = "Internal Server Error", response=APIError.class) //
	})
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
