package com.qingke.apiplatform.entity;

import lombok.Data;

/**
 * 上传身份证照片
 * @author Administrator
 *
 */
@Data
public class UploadIdCard {
	private int qkNumb;
	private int pNumb;
	private String wxMediaId;
	private String side;
}
