package com.moneytransfer.api.filter;

import com.moneytransfer.constants.MoneyTransferConstant;

import static spark.Spark.after;

public class AfterFilter {
	public AfterFilter() {
		after(((request, response) -> {
			response.header(MoneyTransferConstant.CONTENT_TYPE, MoneyTransferConstant.APPLICATION_JSON);
		}));
	}
}
