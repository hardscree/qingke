package com.qingke.apiplatform.entity;

import lombok.Data;

@Data
public class ApiToken {
	private String token;
	private int expires;
}
