package com.qingke.apiplatform.util;

import org.apache.commons.lang3.StringUtils;

public class ProjectPathUtil {

	public static String getFilePath(String folder) {
		if(StringUtils.isBlank(folder))
			folder = "other";
		
		return "static/upload/"+folder+"/";
	}
	public static String getResourcePath(String folder) {
		if(StringUtils.isBlank(folder))
			folder = "other";
		
		return "resources/upload/"+folder+"/";
	}
	
	
}
