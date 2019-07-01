package com.moneytransfer;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.moneytransfer.api.routes.AccountRoutes;
import com.moneytransfer.api.routes.SwaggerRoutes;
import com.moneytransfer.api.routes.TransferRoutes;
import com.moneytransfer.configuration.APIConfiguration;
import com.moneytransfer.configuration.GuiceModule;
import com.moneytransfer.model.Validator;
import com.moneytransfer.services.AccountServiceImpl;
import com.moneytransfer.services.TransferServiceImpl;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@SwaggerDefinition(host = "localhost:4567", //
		info = @Info(description = "Money Transfer API", //
				version = "V1.0", //
				title = "Fund transfer api", //
				contact = @Contact(name = "Serol", url = "https://serol.ro") ) , //
				schemes = { SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS }, //
				consumes = { "application/json" }, //
				produces = { "application/json" }, //
				tags = { @Tag(name = "swagger") })
public class MoneyTransferMain {

	public static void main(String[] args) throws Exception {
		initializeInjectors();
		//Initialize API Configuration
		new APIConfiguration();
	}

	private static void initializeInjectors() {
		//Initialize DI modules
		Injector injector = Guice.createInjector(new GuiceModule());
		injector.getInstance(AccountRoutes.class);
		injector.getInstance(TransferRoutes.class);
		injector.getInstance(AccountServiceImpl.class);
		injector.getInstance(TransferServiceImpl.class);
		injector.getInstance(Validator.class);
		injector.getInstance(SwaggerRoutes.class);
	}
}
