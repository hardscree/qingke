package com.qingke.apiplatform.controller;

import java.util.HashMap;
import java.util.Map;

import com.qingke.apiplatform.entity.enums.SmsTypeEnum;
import com.qingke.apiplatform.services.impl.AliSmsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qingke.apiplatform.core.Annotations.PassToken;
import com.qingke.apiplatform.entity.RestResult;
import com.qingke.apiplatform.entity.SortMsg;
import com.qingke.apiplatform.services.AuthCodeService;
import com.qingke.apiplatform.util.ResultUtil;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/authcode")
public class AuthCodeController {

	@Autowired
	private AuthCodeService authCodeService;

	@Autowired
	private AliSmsServiceImpl smsService;
	
	
	@PassToken
	@PostMapping("/send")
    @ApiOperation("发送验证码")
	public RestResult<String> send(@RequestBody SortMsg sortMsg) {
		String code = authCodeService.getCode(sortMsg.getMobile(), 600);
		sortMsg.setType(SmsTypeEnum.AuthCode);
		Map<String, String> templateData = new HashMap<>(1);
		templateData.put("code", code);
		return ResultUtil.success(smsService.send(sortMsg,templateData)?"true":"false");
	}
	
}
