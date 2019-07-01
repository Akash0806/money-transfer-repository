package com.moneytransfer.api.routes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moneytransfer.api.SwaggerParser;

import static spark.Spark.get;

public class SwaggerRoutes {
    public static final String APP_PACKAGE = "com.moneytransfer.api.routes";

    public SwaggerRoutes() {
        initializeTransferRoutes();
    }

    private void initializeTransferRoutes() {
        final String swaggerJson;
        try {
            swaggerJson = SwaggerParser.getSwaggerJson(APP_PACKAGE);
            get("/swagger", (req, res) -> {
                return swaggerJson;
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }



}
