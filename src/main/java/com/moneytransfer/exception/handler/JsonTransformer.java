package com.moneytransfer.exception.handler;

import com.google.gson.Gson;

public class JsonTransformer {

	public static String toJson(Object o) {
		return new Gson().toJson(o);
	}
}
