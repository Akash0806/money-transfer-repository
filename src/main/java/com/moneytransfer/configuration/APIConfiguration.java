package com.moneytransfer.configuration;

import com.moneytransfer.api.filter.AfterFilter;
import com.moneytransfer.api.routes.AccountRoutes;
import com.moneytransfer.api.routes.SwaggerRoutes;
import com.moneytransfer.api.routes.TransferRoutes;
import com.moneytransfer.exception.handler.GlobalExceptionHandler;

public class APIConfiguration {
	public APIConfiguration() throws Exception {
		Class[] classes = {AccountRoutes.class, TransferRoutes.class,
								GlobalExceptionHandler.class, AfterFilter.class, SwaggerRoutes.class};
		for(Class clazz: classes) {
			clazz.getDeclaredConstructors()[0].newInstance();
		}
	}
}
