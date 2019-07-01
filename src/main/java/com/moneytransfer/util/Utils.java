package com.moneytransfer.util;

import com.google.gson.Gson;
import com.moneytransfer.exception.DataValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {

	private static final Gson gson = new Gson();

	public static String getObjectToJsonString(Object o) {
		return gson.toJson(o);
	}

	public static String limitString(String str, int len) {
		if(str.length() < len) {
			return str;
		} else {
			return str.substring(0, len).concat("...");
		}
	}

	public static LocalDate parseStringToDate(String date) {
		try {
			LocalDate dt = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
			if(dt.isBefore(LocalDate.now())) {
				throw new DataValidationException(ErrorEnum.PAST_TRANSFER_DATE);
			}
			return dt;
		} catch (DataValidationException e) {
			throw  e;
		} catch (Exception e) {
			throw new DataValidationException(ErrorEnum.INVALID_DATE_FORMAT);
		}
	}
}
