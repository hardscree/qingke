package com.qingke.apiplatform.entity;

import java.util.Map;

import lombok.Data;

@Data
public class AliSortMessage {

	private String mobile;
	private String templateCode;
	private Map<String, String> templateData;
}
