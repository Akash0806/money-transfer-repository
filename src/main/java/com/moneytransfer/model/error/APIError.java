package com.moneytransfer.model.error;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class APIError {
	private int errorCode;
	private String message;
	private String type;
	private String additionalInfo;
}
