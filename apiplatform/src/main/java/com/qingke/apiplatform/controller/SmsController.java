package com.qingke.apiplatform.controller;

import com.qingke.apiplatform.services.impl.AliSmsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qingke.apiplatform.core.Annotations.PassToken;
import com.qingke.apiplatform.entity.RestResult;
import com.qingke.apiplatform.entity.SortMsg;
import com.qingke.apiplatform.util.ResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/sms")
@Api("用户相关API接口")
public class SmsController {

	@Autowired
	AliSmsServiceImpl aliSmsService;

	@PassToken
	@PostMapping("/send")
	@ApiOperation("发送短信")
	public RestResult<String> send(@RequestBody SortMsg msg) {
		return ResultUtil.success(aliSmsService.send(msg) ? "true" : "false");
	}

}
