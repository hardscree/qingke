package com.qingke.apiplatform.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qingke.apiplatform.core.Annotations.PassToken;
import com.qingke.apiplatform.core.Annotations.QingkeLoginToken;
import com.qingke.apiplatform.core.Annotations.UserLoginToken;
import com.qingke.apiplatform.core.configurer.WxMpConfiguration;
import com.qingke.apiplatform.entity.ApiToken;
import com.qingke.apiplatform.entity.FaceCallback;
import com.qingke.apiplatform.entity.FaceToken;
import com.qingke.apiplatform.entity.IdCardInfo;
import com.qingke.apiplatform.entity.LoginResult;
import com.qingke.apiplatform.entity.QingkeLicense;
import com.qingke.apiplatform.entity.QingkeSignOn;
import com.qingke.apiplatform.entity.RestResult;
import com.qingke.apiplatform.entity.UploadIdCard;
import com.qingke.apiplatform.model.Project;
import com.qingke.apiplatform.model.ProjectQingke;
import com.qingke.apiplatform.model.Qingke;
import com.qingke.apiplatform.model.QingkeFace;
import com.qingke.apiplatform.services.AuthCodeService;
import com.qingke.apiplatform.services.CodeMessageService;
import com.qingke.apiplatform.services.FaceIdService;
import com.qingke.apiplatform.services.OcrService;
import com.qingke.apiplatform.services.ProjectQingkeService;
import com.qingke.apiplatform.services.ProjectService;
import com.qingke.apiplatform.services.QingkeFaceService;
import com.qingke.apiplatform.services.QingkeService;
import com.qingke.apiplatform.services.TokenService;
import com.qingke.apiplatform.util.Base64ImageUtil;
import com.qingke.apiplatform.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/qingke")
@Api("用户相关API接口")
public class QingkeController {
	@Autowired
	private TokenService tokenService;

	@Autowired
	private OcrService ocrService;

	@Autowired
	private AuthCodeService authcodeService;

	@Autowired
	private ProjectQingkeService pqService;

	@Autowired
	private QingkeFaceService qfService;

	@Value("${weixin.appId}")
	private String appId;

	@Value("${qingke.image.host}")
	private String imgHost;
	
	@Value("${qingke.frontend}")
	private String frontendUrl;

	@Value("${qingke.filePath}")
	private String filePathConfig;
	@Value("${qingke.fileMapName}")
	private String fileMapNameConfig;

	@Value("${faceid.returnUrl}")
	private String returnUrl;

	@Value("${faceid.notifyUrl}")
	private String notifyUrl;

	@Autowired
	private QingkeService qingkeService;

	@Autowired
	private CodeMessageService codeMsgService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private FaceIdService faceIdService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PassToken
	@PostMapping("/login")
	@ApiOperation("用户登录接口")
	public RestResult<LoginResult> login(@RequestBody QingkeSignOn info) {
		try {

			if (StringUtils.isBlank(info.getQkPhone()) || StringUtils.isBlank(info.getAuthCode()))
				return ResultUtil.failed(500, "请提供手机号和验证码", null);

			if (!authcodeService.checkCode(info.getQkPhone(), info.getAuthCode())) {
				return ResultUtil.failed(500, "验证码不正确或已过期", null);
			}

			ApiToken apiToken = tokenService.getQingkeToken(info.getQkPhone());
			LoginResult result = new LoginResult();

			result.setToken(apiToken.getToken());
			result.setTokeyExpire(apiToken.getExpires());

			// getuser
			Qingke qingke = qingkeService.getByMobile(info.getQkPhone());
			if (qingke == null && !StringUtils.isBlank(info.getQkOpenId())) {

				qingke = qingkeService.getByOpenId(info.getQkOpenId());
				if (qingke != null && !qingke.getQkPhone().equals(info.getQkPhone())) {
					return ResultUtil.failed(500, "此微信已绑定手机号", null);
				}
			}
			if (qingke == null) {// if not exist then create
				try {
					String openId = info.getQkOpenId();
					if (StringUtils.isBlank(openId)) {
						final WxMpService wxService = WxMpConfiguration.getMpServices().get(appId);
						WxMpOAuth2AccessToken oAuth2AccessToken = wxService.oauth2getAccessToken(info.getCode());
						openId = oAuth2AccessToken.getOpenId();
					}
					
					if (!StringUtils.isBlank(openId)) {
						qingke = qingkeService.getByOpenId(openId);
						if (qingke != null) {
							return ResultUtil.failed(500, "此微信已绑定手机号！", null);
						}
					}
					qingke = new Qingke();
					qingke.setQkOpenid(openId);
					qingke.setQkCreate(new Date());
					qingke.setQkSex(false);

					qingke.setQkidStatus(1);
					qingke.setQkLicenseStatus(0);
					qingke.setQkVideoStatus(1);
					qingke.setQkSignStatus(1);

					qingke.setQkPhone(info.getQkPhone());

					qingkeService.Insert(qingke);
					qingke = qingkeService.getByMobile(info.getQkPhone());
				} catch (WxErrorException e) {
					e.printStackTrace();
					logger.info(e.getMessage() + e.getStackTrace());
					return ResultUtil.failed(500, e.getMessage(), null);
				}

			}
			result.setQingke(qingke);

			ProjectQingke pQingke = null;
			// select a project-no by openId from codemessage
			int pNumb = codeMsgService.getLatestPnumb(qingke.getQkOpenid());
			if (pNumb != 0) {

				Project project = projectService.getById(pNumb);
				if (project == null || project.getpStatus().equals(5)) {// 5=作废
					return ResultUtil.failed(404, "未找到对应项目", null);
				}

				pQingke = pqService.selectByQkNumbAndPNumb(qingke.getQkNumb(), pNumb);
				// if project-qk not exist then create
				if (pQingke == null) {
					pQingke = new ProjectQingke();
					pQingke.setFocusTime(new Date());
					pQingke.setpNumb(pNumb);
					pQingke.setpQkStatus(1);
					pQingke.setSignStatus(1);
					pQingke.setQkNumb(qingke.getQkNumb());
					pQingke.setStatusUpdatetime(new Date());

					pqService.insert(pQingke);
				}
			}
			if (pQingke == null) {
				return ResultUtil.failed(404, "未找到对应项目", null);
			}
			result.setProjectQingke(pQingke);

			return ResultUtil.success(result);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info(ex.getMessage() + ex.getStackTrace());
			return ResultUtil.failed(500, "系统繁忙请稍后使用！", null);
		}
	}

	@QingkeLoginToken
	@PostMapping("/info")
	@ApiOperation("获取指定轻客信息")
	public RestResult<LoginResult> getQingkeInfo(@RequestBody QingkeSignOn info) {
		try {

			LoginResult result = new LoginResult();

			result.setToken("");
			result.setTokeyExpire(0);

			// getuser
			Qingke qingke = qingkeService.getByMobile(info.getQkPhone());
			if (qingke == null) {
				return ResultUtil.failed(4004, "未找到对应轻客", null);
			}
			result.setQingke(qingke);

			ProjectQingke pQingke = null;
			// select a project-no by openId from codemessage
			int pNumb = codeMsgService.getLatestPnumb(qingke.getQkOpenid());
			if (pNumb != 0) {

				Project project = projectService.getById(pNumb);
				if (project == null || project.getpStatus().equals(5)) {// 5=作废
					return ResultUtil.failed(404, "未找到对应项目", null);
				}

				pQingke = pqService.selectByQkNumbAndPNumb(qingke.getQkNumb(), pNumb);
				// if project-qk not exist then create
				if (pQingke == null) {
					pQingke = new ProjectQingke();
					pQingke.setFocusTime(new Date());
					pQingke.setpNumb(pNumb);
					pQingke.setQkNumb(qingke.getQkNumb());
					pQingke.setpQkStatus(1);
					pQingke.setSignStatus(1);
					pQingke.setStatusUpdatetime(new Date());

					pqService.insert(pQingke);
				}
			}
			if (pQingke == null) {
				return ResultUtil.failed(404, "未找到对应项目", null);
			}
			result.setProjectQingke(pQingke);

			return ResultUtil.success(result);
		} catch (Exception ex) {
			logger.info(ex.getMessage() + ex.getStackTrace());
			return ResultUtil.failed(500, "系统繁忙请稍后使用！", null);
		}
	}

	@QingkeLoginToken
	@PostMapping("/idCardInfo")
	@ApiOperation("确认身份证信息")
	public RestResult<String> confirmIdCardInfo(@RequestBody IdCardInfo info) {
		String json = JSON.toJSONString(info);
		System.out.println(json);
		Qingke qingke = JSON.parseObject(json, Qingke.class);
		qingke.setQkidStatus(2);
		boolean rst = qingkeService.UpdateSelective(qingke);
		return ResultUtil.success(rst ? "true" : "false");
	}

	@QingkeLoginToken
	@PostMapping("/idCardPic")
	@ApiOperation("上传身份证图片")
	public RestResult<Map<String, String>> uploadIdCard(@RequestBody UploadIdCard card) {
		try {
			String folder = "idcard/";

			// File path = new File(ResourceUtils.getURL("classpath:").getPath());
			String filePath = filePathConfig + folder;
			String urlPath = fileMapNameConfig + "/" + folder;
			File upload = new File(filePath);
			if (!upload.exists()) {
				upload.mkdirs();
			}

			String fileName = card.getQkNumb() + "_" + UUID.randomUUID().toString() + ".jpg";
			File dest = new File(filePath + fileName);

			final WxMpService wxService = WxMpConfiguration.getMpServices().get(appId);
			// ((WxMpInMemoryConfigStorage)wxService.getWxMpConfigStorage()).setTmpDirFile(upload);

			// 这里这个file底层是个临时文件//File.createTempFile(prefix, suffix, directory)
			File file = wxService.getMaterialService().mediaDownload(card.getWxMediaId());
			this.logger.info("\n接收到idcard临时文件：[{}, {}, {}, {}]", file.getPath(), file.getName(), file.length(),
					card.getQkNumb());

			if (file.length() > 1.5 * 1024 * 1024) {
				return ResultUtil.failed(501, "图像过大，无法解析", null);
			}
			// get info
			Map<String, String> data = ocrService.getIdCardInfo(file, card.getSide());

			// save db
			if (data != null && data.containsKey("side")) {

				FileUtils.copyFile(file, dest);
				Qingke qingke = new Qingke();
				String side = data.get("side");
				String url = urlPath + fileName;
				if (side.equals("back")) {
					qingke.setQkidBackphotoUrl(url);
				} else {
					qingke.setQkidFrontphotoUrl(url);
				}
				qingke.setQkNumb(card.getQkNumb());
				qingkeService.UpdateSelective(qingke);

				ProjectQingke pq = new ProjectQingke();
				pq.setpNumb(card.getPNumb());
				pq.setQkNumb(card.getQkNumb());
//				pq.setpQkStatus(2);// 审核中
				pq.setStatusUpdatetime(new Date());

				pqService.updateSelective(pq);
				return ResultUtil.success(data);
			}
		} catch (Exception e) {
			this.logger.info("\n{}{}", e.getMessage(), e.getStackTrace());
			e.printStackTrace();

		}
		return ResultUtil.failed(500, "图像无法正确解析", null);
	}

//	@QingkeLoginToken
//	@GetMapping("/videoCode")
//	@ApiOperation("获取视频验证码")
//	public RestResult<FaceRandom> getVideoCode(){
//		return ResultUtil.success(faceIdService.getRandom());
//	}
	
	@PostMapping("/returnUrl")
	@ApiOperation("faceid回跳地址")
	public void Redirect(HttpServletResponse response) throws IOException {
		response.sendRedirect(frontendUrl);
	} 

	@QingkeLoginToken
	@GetMapping("/videoToken")
	@ApiOperation("获取faceid token")
	public RestResult<FaceToken> getFaceToken(int qkNumb) {
		String bizNo = qkNumb + "_" + System.currentTimeMillis();
		Qingke qingke = qingkeService.getById(qkNumb);
		String idCardName = qingke.getQkName();
		String idCardNumb = qingke.getQkId();
		String rtUrl = returnUrl + "?bizNo=" + bizNo;
		try {
			JSONObject jsonObject = faceIdService.getPageToken(rtUrl, notifyUrl, bizNo, qkNumb + "", idCardName,
					idCardNumb, "", "");

			if (jsonObject.containsKey("token")) {
				FaceToken token = new FaceToken();
				token.setToken(jsonObject.getString("token"));
				token.setBizNo(bizNo);
				token.setBizId(jsonObject.getString("biz_id"));
				return ResultUtil.success(token);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info(ex.getMessage() + ex.getStackTrace());
		}
		return ResultUtil.failed(404, "Not found", null);
	}

	@PassToken
	@PostMapping("/video/result")
	@ApiOperation("faceid 回调接口")
	public RestResult<String> CallBack(FaceCallback result) {
		logger.info(JSON.toJSONString(result));
		JSONObject data = JSONObject.parseObject(result.getData());

		String status = data.getString("status");
		String bizId = "";
		String bizNumb = "";
		String qkNumb = "";
		String rst = "";
		String confidence = "";
		String timeUsed = "";// time_used

		if (status.equals("OK")) {
			JSONObject bizInfo = data.getJSONObject("biz_info");
			bizId = bizInfo.getString("biz_id");
			bizNumb = bizInfo.getString("biz_no");
			qkNumb = bizInfo.getString("biz_extra_data");

			rst = data.getJSONObject("liveness_result").getString("result");
			timeUsed = data.getString("time_used");			

			if(data.containsKey("verify_result")) {
				JSONObject verifyResult = data.getJSONObject("verify_result");
				if(verifyResult!=null && verifyResult.containsKey("result_faceid")) {
					confidence = verifyResult.getJSONObject("result_faceid").getString("confidence");
				}
			}
			// save db

			Qingke qk = new Qingke();
			qk.setQkNumb(Integer.parseInt(qkNumb));
			qk.setQkVideoRate(confidence);
			if(rst.equals("PASS")) {
				qk.setQkVideoStatus(2);
			}
			qingkeService.UpdateSelective(qk);
		}

		QingkeFace qf = new QingkeFace();
		qf.setBizId(bizId);
		qf.setBizNo(bizNumb);
		qf.setQkNumb(qkNumb);
		qf.setJson(data.toJSONString());
		qf.setRate(confidence);
		qf.setStatus(status);
		qf.setResult(rst);
		qf.setTimeUsed(timeUsed);

		qfService.insert(qf);

		return ResultUtil.success(null);
	}

	@UserLoginToken
	@GetMapping("/videoImage")
	@ApiOperation("更新轻客视频图片")
	public RestResult<String> getImageBest(String qkNumb) {
		QingkeFace qf = qfService.getLatestOkResult(qkNumb);
		if (qf == null) {
			return ResultUtil.failed(404, "NOT FOUND", null);
		}
		try {
			JSONObject jsonObject = faceIdService.getResult(qf.getBizId());

			if (jsonObject != null && jsonObject.containsKey("images")) {
				JSONObject images = jsonObject.getJSONObject("images");
				if (images != null && images.containsKey("image_best")) {
					String imgdata = images.getString("image_best");
					if (!StringUtils.isBlank(imgdata) && imgdata.length() > 24) {
						// save file
						String fileName = qkNumb + "_" + UUID.randomUUID().toString() + ".jpg";
						String folder = "video/";

						// File path = new File(ResourceUtils.getURL("classpath:").getPath());
						String filePath = filePathConfig + folder;
						String urlPath = fileMapNameConfig + "/" + folder;
						File upload = new File(filePath);
						if (!upload.exists()) {
							upload.mkdirs();
						}
						Base64ImageUtil.generateImage(imgdata, filePath + fileName);
						Qingke qk = new Qingke();
						qk.setQkNumb(Integer.parseInt(qkNumb));
						qk.setQkVideoUrl(urlPath + fileName);
						qingkeService.UpdateSelective(qk);

						return ResultUtil.success(imgHost + urlPath + fileName);
					}

				}
			}
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			logger.info(ex.getMessage() + ex.getStackTrace());
		}

		return ResultUtil.failed(404, "NOT FOUND", null);
	}

//	@QingkeLoginToken
//	@GetMapping("/video/result")
//	@ApiOperation("获取视频验证结果")
//	public RestResult<QingkeFace> getValidateResult(String bizNo) {
//		QingkeFace result = qfService.getLatestByBizNo(bizNo);
//		return ResultUtil.success(result);
//	}

//	@QingkeLoginToken
//	@PostMapping("/video")
//	@ApiOperation("上传活体认证视频")
//	public RestResult<String> uploadVideo(HttpServletRequest request, HttpServletResponse response,
//			@RequestParam("video") MultipartFile file) {
//		try {
//			if (file == null || file.getSize() > 1024 * 1024 * 20) {
//				return ResultUtil.failed(400, "超过视频大小限制", null);
//			}
//
//			String qkNumb = request.getParameter("qkNumb");
//			String originalFilename = file.getOriginalFilename();
//			String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
//
//			// save file
//			String fileName = qkNumb + "_" + UUID.randomUUID().toString() + "." + ext;
//			String folder = "video/";
//
//			// File path = new File(ResourceUtils.getURL("classpath:").getPath());
//			String filePath = filePathConfig + folder;
//			String urlPath = fileMapNameConfig + "/" + folder;
//			File upload = new File(filePath);
//			if (!upload.exists()) {
//				upload.mkdirs();
//			}
//
//			File dest = new File(filePath + fileName);
//			FileUtils.writeByteArrayToFile(dest, file.getBytes());
//
//			Qingke qingke = new Qingke();
//			qingke.setQkVideoUrl(urlPath + fileName);
//			qingke.setQkNumb(Integer.parseInt(qkNumb));
//			qingke.setQkVideoStatus(2);
//			qingkeService.UpdateSelective(qingke);
//
//			return ResultUtil.success("true");
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return ResultUtil.failed(500, "出现异常", null);
//	}
//
//	@QingkeLoginToken
//	@PostMapping("/video/temp")
//	@ApiOperation("上传活体认证视频")
//	public RestResult<String> uploadVideoTmp(HttpServletRequest request, HttpServletResponse response,
//			@RequestParam("video") MultipartFile file) {
//		long startTime = System.currentTimeMillis();
//		try {
//			if (file == null || file.getSize() > 1024 * 1024 * 20) {
//				return ResultUtil.failed(400, "超过视频大小限制", null);
//			}
//
//			String qkNumb = request.getParameter("qkNumb");
//			String originalFilename = file.getOriginalFilename();
//			String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
//
//			// save file
//			String fileName = qkNumb + "_" + UUID.randomUUID().toString() + "." + ext;
//			String folder = "video/";
//
//			// File path = new File(ResourceUtils.getURL("classpath:").getPath());
//			String filePath = filePathConfig + folder;
//			String urlPath = fileMapNameConfig + "/" + folder;
//			File upload = new File(filePath);
//			if (!upload.exists()) {
//				upload.mkdirs();
//			}
//
//			File dest = new File(filePath + fileName);
//			FileUtils.writeByteArrayToFile(dest, file.getBytes());
//			long endTime = System.currentTimeMillis(); // 获取结束时间
//			this.logger.info("===========duration 1:" + (endTime - startTime) + "ms");
//
//			// vidate video
//			String tokenRandom = request.getParameter("tokenRandomNumber");
//
//			JSONObject valiRst = faceIdService.validateVideo(dest, tokenRandom);
//			this.logger.info("===========validate: " + valiRst.toJSONString());
//			endTime = System.currentTimeMillis(); // 获取结束时间
//			this.logger.info("===========duration 2:" + (endTime - startTime) + "ms");
//			System.out.println(valiRst);
//			if (valiRst.containsKey("error_message")) {
//				String errKey = valiRst.getString("error_message");
//				String err = faceIdService.getErrDesc(errKey);
//				FileUtils.deleteQuietly(dest);
//				return ResultUtil.failed(500, err, errKey);
//			}
//
//			String token = valiRst.getString("token_video");
//			Qingke qk = qingkeService.getById(Integer.parseInt(qkNumb));
//			JSONObject json = faceIdService.verify(qk.getQkName(), qk.getQkId(), token);
//			this.logger.info("===========verify: " + json.toJSONString());
//			endTime = System.currentTimeMillis(); // 获取结束时间
//			this.logger.info("===========duration 3 :" + (endTime - startTime) + "ms");
//			if (json.containsKey("error_message")) {
//				String errKey = json.getString("error_message");
//				String err = faceIdService.getErrDesc(errKey);
//				FileUtils.deleteQuietly(dest);
//				return ResultUtil.failed(500, err, errKey);
//			}
//
//			JSONObject liveness = json.getJSONObject("liveness");
//			if (!liveness.containsKey("procedure_validation")
//					|| !liveness.getString("procedure_validation").equals("PASSED")) {
//				String errKey = liveness.getString("procedure_validation");
//				String err = faceIdService.getErrDesc(errKey);
//				FileUtils.deleteQuietly(dest);
//				return ResultUtil.failed(500, err, errKey);
//			}
//			if (!liveness.containsKey("face_genuineness") || !liveness.getString("face_genuineness").equals("PASSED")) {
//				String errKey = liveness.getString("procedure_validation");
//				String err = faceIdService.getErrDesc(errKey);
//				FileUtils.deleteQuietly(dest);
//				return ResultUtil.failed(500, err, errKey);
//			}
//			String rate = "";
//			if (json.containsKey("result_faceid")) {
//				JSONObject resultJson = json.getJSONObject("result_faceid");
//				if (resultJson != null && resultJson.containsKey("confidence")) {
//					rate = resultJson.getString("confidence");
//				}
//			}
//
//			Qingke qingke = new Qingke();
//			qingke.setQkVideoUrl(urlPath + fileName);
//			qingke.setQkNumb(Integer.parseInt(qkNumb));
//			qingke.setQkVideoStatus(2);
//			qingke.setQkVideoRate(rate);
//			qingkeService.UpdateSelective(qingke);
//
//			endTime = System.currentTimeMillis(); // 获取结束时间
//			this.logger.info("===========duration total :" + (endTime - startTime) + "ms");
//			return ResultUtil.success("true");
//
////			return ResultUtil.success("true");
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return ResultUtil.failed(500, "出现异常", null);
//	}

	@QingkeLoginToken
	@GetMapping("/licence")
	@ApiOperation("获取指定用户的营业执照信息")
	public RestResult<QingkeLicense> getLicence(int qkNumb) {
		Qingke qingke = qingkeService.getById(qkNumb);
		if (qingke != null) {
			QingkeLicense ql = new QingkeLicense();
			ql.setQkNumb(qkNumb);
			ql.setQkLicenseUrl(imgHost + qingke.getQkLicenseUrl());
			ql.setQkLicenseStatus(qingke.getQkLicenseStatus());
			return ResultUtil.success(ql);
		}
		return ResultUtil.failed(404, "Not Found", null);
	}

}
