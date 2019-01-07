package com.qingke.apiplatform.services;

import java.io.File;
import java.util.Map;

public interface OcrService {
	//Map<String, String> getIdCardInfo(byte[] bytes);
	Map<String, String> getIdCardInfo(File file, String side);
}
