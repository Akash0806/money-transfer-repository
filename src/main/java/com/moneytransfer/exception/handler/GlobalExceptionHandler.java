package com.moneytransfer.exception.handler;

import com.moneytransfer.exception.*;
import com.moneytransfer.model.error.APIError;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.moneytransfer.util.Utils.getObjectToJsonString;
import static com.moneytransfer.util.Utils.limitString;
import static org.eclipse.jetty.http.HttpStatus.INTERNAL_SERVER_ERROR_500;
import static spark.Spark.exception;

/**
 * Global exception handler class to handle all types of application
 * exception and format for client call when error occurs.
 * *
 */
public class GlobalExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	public GlobalExceptionHandler() {
		initializeExceptionHandler();
	}

	private void initializeExceptionHandler() {
		exception(Exception.class, (e, req, res) -> {
			APIError apiError;
			if(e instanceof DataValidationException) {
				res.status(HttpStatus.BAD_REQUEST_400);
				DataValidationException exception = (DataValidationException) e;
				apiError= APIError.builder()
						.errorCode(exception.getErrorCode())
						.message(e.getMessage())
						.type(exception.getType())
						.build();
			} else if(e instanceof AmountOverdrawnException) {
				res.status(HttpStatus.PAYMENT_REQUIRED_402);
				AmountOverdrawnException exception = (AmountOverdrawnException) e;
				apiError = APIError.builder()
						.errorCode(exception.getErrorCode())
						.message(e.getMessage())
						.type(exception.getType())
						.build();
			} else if(e instanceof TransactionException) {
				res.status(HttpStatus.PAYMENT_REQUIRED_402);
				TransactionException exception = (TransactionException) e;
				apiError = APIError.builder()
						.errorCode(exception.getErrorCode())
						.message(e.getMessage())
						.type(exception.getType())
						.build();
			} else if(e instanceof AccountNotFoundException) {
				res.status(HttpStatus.NOT_FOUND_404);
				AccountNotFoundException exception = (AccountNotFoundException) e;
				apiError = APIError.builder()
						.errorCode(exception.getErrorCode())
						.message(e.getMessage())
						.type(exception.getType())
						.build();
			} else if(e instanceof MoneyTransferBaseException) {
				res.status(INTERNAL_SERVER_ERROR_500);
				MoneyTransferBaseException exception = (MoneyTransferBaseException) e;
				apiError = APIError.builder()
						.errorCode(exception.getErrorCode())
						.type(exception.getType())
						.message(e.getMessage())
						.build();
			} else {
				res.status(INTERNAL_SERVER_ERROR_500);
				 apiError = APIError.builder()
						.errorCode(INTERNAL_SERVER_ERROR_500)
						.message(e.getMessage() != null ? limitString(e.getMessage(), 100)
										: limitString(e.toString(), 100))
						.type(HttpStatus.getMessage(INTERNAL_SERVER_ERROR_500))
						.build();
			}
			LOGGER.info("Exception handler message: {}", e.getMessage());

			res.body(getObjectToJsonString(apiError));
			res.header("Content-Type", "application/json");
		});
	}
}
