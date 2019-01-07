package com.qingke.apiplatform.entity;

import com.qingke.apiplatform.entity.enums.SmsTypeEnum;
import lombok.Data;

@Data
public class SortMsg {

	private String mobile;
	private SmsTypeEnum type;
}
