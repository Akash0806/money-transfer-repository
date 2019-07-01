package com.moneytransfer.model.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequest {
	private String accountNumber;
	private String alias;
	private String type;
	private BigDecimal amount;
	private String currency;
}
