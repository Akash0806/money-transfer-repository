package com.moneytransfer.model.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AccountResponseList {
	List<AccountResponse> accounts;
}
