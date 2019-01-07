package com.qingke.apiplatform.controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qingke.apiplatform.core.Annotations.QingkeLoginToken;
import com.qingke.apiplatform.entity.ProjectSign;
import com.qingke.apiplatform.entity.RestResult;
import com.qingke.apiplatform.model.ProjectQingke;
import com.qingke.apiplatform.model.Protocol;
import com.qingke.apiplatform.model.Qingke;
import com.qingke.apiplatform.services.ProjectQingkeService;
import com.qingke.apiplatform.services.ProjectService;
import com.qingke.apiplatform.services.QingkeService;
import com.qingke.apiplatform.util.Base64ImageUtil;
import com.qingke.apiplatform.util.ResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/project")
@Api("项目相关API接口")
public class ProjectController {

	@Autowired
	private ProjectQingkeService pqService;

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private QingkeService qingkeService;

	@Value("${qingke.filePath}")
	private String filePathConfig;
	@Value("${qingke.fileMapName}")
	private String fileMapNameConfig;

	@QingkeLoginToken
	@GetMapping("/contract")
	@ApiOperation("获取指定项目协议")
	public RestResult<List<Protocol>> getContract(Integer id) {
		List<Protocol> list = projectService.getProtocolsByProjectId(id);
		return ResultUtil.success(list);
	}

	@QingkeLoginToken
	@PostMapping("/contract")
	@ApiOperation("签属协议")
	public RestResult<String> sign(@RequestBody ProjectSign ps) {

		String fileName = ps.getQkNumb() + "_" + ps.getpNumb() + "_" + UUID.randomUUID().toString() + ".png";
		String folder = "contract/";

		// path = new File(ResourceUtils.getURL("classpath:").getPath());
		String filePath = filePathConfig + folder;
		String urlPath = fileMapNameConfig + "/" + folder;

		File upload = new File(filePath);
		if (!upload.exists()) {
			upload.mkdirs();
		}

		if (Base64ImageUtil.generateImage(ps.getData(), filePath + fileName)) {
			// save db

			ProjectQingke pq = new ProjectQingke();
			pq.setpNumb(ps.getpNumb());
			pq.setQkNumb(ps.getQkNumb());
			//pq.setpQkStatus(2);// 审核中
			pq.setSignUrl(urlPath + fileName);
			pq.setSignStatus(2);// 已提交
			pq.setStatusUpdatetime(new Date());

			boolean rst = pqService.updateSelective(pq);
			if(rst){
				Qingke qingke = qingkeService.getById(ps.getQkNumb());
				if(qingke!=null && (
						qingke.getQkSignStatus() ==1 
						|| qingke.getQkSignStatus() == 4
						|| StringUtils.isBlank(qingke.getQkSignUrl())
						)){
					Qingke qk = new Qingke();
					qk.setQkNumb(ps.getQkNumb());
					qk.setQkSignStatus(2);
					qk.setQkSignUrl(urlPath + fileName);
					qingkeService.UpdateSelective(qk);
				}
				
				
				return ResultUtil.success("true");
			}
				
		}

		return ResultUtil.failed(500, "系统繁忙请稍后使用！", "false");
	}
}
