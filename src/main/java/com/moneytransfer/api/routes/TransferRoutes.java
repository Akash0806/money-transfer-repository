package com.moneytransfer.api.routes;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.moneytransfer.constants.MoneyTransferConstant;
import com.moneytransfer.exception.handler.JsonTransformer;
import com.moneytransfer.model.request.TransferRequest;
import com.moneytransfer.model.response.TransferResponse;
import com.moneytransfer.services.TransferService;
import io.swagger.annotations.*;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import com.moneytransfer.model.error.APIError;
import static spark.Spark.get;
import static spark.Spark.post;

/**
 * This class serves all transfer related resources such as transfer immediate,
 * finding an existing transfer detail.
 *
 *
 */
@Api
@Path(MoneyTransferConstant.V_1_TRANSFER)
@Produces("application/json")
public class TransferRoutes {
	@Inject
	private TransferService transferService;

	public TransferRoutes() {
		initializeTransferRoutes();
	}

	@POST
	@ApiOperation(value = "Immediate Money Transfer", nickname="Fund Transfer")
	@ApiImplicitParams({ //
			@ApiImplicitParam(required = true, dataType = "com.moneytransfer.model.request.TransferRequest", paramType = "body") //
	}) //
	@ApiResponses(value = { //
			@ApiResponse(code = 200, message = "Success", response= TransferResponse.class), //
			@ApiResponse(code = 400, message = "Transfer amount can't be negative", response=APIError.class), //
			@ApiResponse(code = 400, message = "BAD Request", response=APIError.class), //
			@ApiResponse(code = 402, message = "Transfer amount exceeds available balance", response=APIError.class), //
			@ApiResponse(code = 404, message = "Account doesn't exists", response=APIError.class) ,//
			@ApiResponse(code = 500, message = "Internal Server Error", response=APIError.class) //
	})
	public void initializeTransferRoutes() {

		post( MoneyTransferConstant.V_1_TRANSFER+"/immediate", ((request, response) -> {

			TransferRequest transferRequest = new Gson().fromJson(request.body(), TransferRequest.class);
			return  transferService.transfer(transferRequest);
		}), JsonTransformer::toJson);

		get( MoneyTransferConstant.V_1_TRANSFER+"/:id", ((request, response) ->
						transferService.findTransferByTransId(Integer.valueOf(request.params(":id")))),
				JsonTransformer::toJson);
	}
}
