package com.qingke.apiplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.qingke.apiplatform.core.Annotations.PassToken;
import com.qingke.apiplatform.core.configurer.WxMpConfiguration;
import com.qingke.apiplatform.entity.RestResult;
import com.qingke.apiplatform.model.Project;
import com.qingke.apiplatform.services.ProjectService;
import com.qingke.apiplatform.util.ResultUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

@RestController
@RequestMapping("api/weixin")
@Api("weixin相关API接口")
public class WeixinController {

	@Autowired
	ProjectService projectService;

	@Value("${weixin.appId}")
	private String appId;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PassToken
	@GetMapping("/jsconfig")
	@ApiOperation("h5页面获取weixin配置")
	public RestResult<WxJsapiSignature> getJsConfig(String url) {
		try {
			final WxMpService wxService = WxMpConfiguration.getMpServices().get(appId);
			WxJsapiSignature signature = wxService.createJsapiSignature(url);
			return ResultUtil.success(signature);
		} catch (WxErrorException e) {
			e.printStackTrace();
			return ResultUtil.failed(500, "系统繁忙请稍后使用！", null);
		}

	}

	@PassToken
	@PostMapping("/qrcode")
	@ApiOperation("生成指定项目二维码")
	public RestResult<WxMpQrCodeTicket> createQrcode(int pNumb) {
		try {
			final WxMpService wxService = WxMpConfiguration.getMpServices().get(appId);
			WxMpQrCodeTicket rst = wxService.getQrcodeService().qrCodeCreateLastTicket(pNumb);

			Project project = new Project();
			project.setpNumb(pNumb);
			project.setWxCodeUrl(rst.getUrl());
			project.setWxTicket(rst.getTicket());

			projectService.UpdateBySelective(project);

			return ResultUtil.success(rst);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		return ResultUtil.failed(500, "系统繁忙请稍后使用！", null);
	}

	//@UserLoginToken
	@PostMapping("/menu")
	@ApiOperation("创建公众号菜单")
	public RestResult<String> createMenu(String menu) {
		try {
			final WxMpService wxService = WxMpConfiguration.getMpServices().get(appId);
			String rst = wxService.getMenuService().menuCreate(menu);
			return ResultUtil.success(rst);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.failed(500, "出现错误", e.getMessage());
		}	
	}

	@PassToken
	@GetMapping(path = "/check", produces = "text/plain;charset=utf-8")
	public String authGet(@RequestParam(name = "signature", required = false) String signature,
			@RequestParam(name = "timestamp", required = false) String timestamp,
			@RequestParam(name = "nonce", required = false) String nonce,
			@RequestParam(name = "echostr", required = false) String echostr) {

		this.logger.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
		if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
			throw new IllegalArgumentException("请求参数非法，请核实!");
		}

		final WxMpService wxService = WxMpConfiguration.getMpServices().get(appId);
		if (wxService == null) {
			throw new IllegalArgumentException(String.format("未找到对应appid=[%d]的配置，请核实！", appId));
		}

		if (wxService.checkSignature(timestamp, nonce, signature)) {
			return echostr;
		}

		return "非法请求";
	}

	@PassToken
	@PostMapping(path = "/check", produces = "application/xml; charset=UTF-8")
	public String post(@RequestBody String requestBody, @RequestParam("signature") String signature,
			@RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce,
			@RequestParam("openid") String openid,
			@RequestParam(name = "encrypt_type", required = false) String encType,
			@RequestParam(name = "msg_signature", required = false) String msgSignature) {
		// final WxMpService wxService = WxMpConfiguration.getMpServices().get(appId);
		this.logger.info(
				"\n接收微信请求：[openid=[{}], [signature=[{}], encType=[{}], msgSignature=[{}],"
						+ " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
				openid, signature, encType, msgSignature, timestamp, nonce, requestBody);

		final WxMpService wxService = WxMpConfiguration.getMpServices().get(appId);
		if (!wxService.checkSignature(timestamp, nonce, signature)) {
			throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
		}

		String out = null;
		if (encType == null) {
			// 明文传输的消息
			WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
			System.out.println(JSON.toJSON(inMessage));
			WxMpXmlOutMessage outMessage = this.route(inMessage, appId);
			if (outMessage == null) {
				return "";
			}

			out = outMessage.toXml();
		} else if ("aes".equalsIgnoreCase(encType)) {
			// aes加密的消息
			WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, wxService.getWxMpConfigStorage(),
					timestamp, nonce, msgSignature);
			this.logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
			WxMpXmlOutMessage outMessage = this.route(inMessage, appId);
			if (outMessage == null) {
				return "";
			}

			out = outMessage.toEncryptedXml(wxService.getWxMpConfigStorage());
		}

		this.logger.debug("\n组装回复信息：{}", out);
		return out;
	}

	private WxMpXmlOutMessage route(WxMpXmlMessage message, String appid) {
		try {
			String event = message.getEvent().toLowerCase();
			if (event.equals("subscribe") || event.equals("scan")) {
				return WxMpConfiguration.getRouters().get(appid).route(message);
			}
		} catch (Exception e) {
			this.logger.error("路由消息时出现异常！", e);
		}

		return null;
	}

}
