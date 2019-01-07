package com.qingke.apiplatform.controller;

import com.qingke.apiplatform.entity.RestResult;
import com.qingke.apiplatform.entity.UploadResponse;
import com.qingke.apiplatform.util.ResultUtil;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/util")
public class UtilController {

	
	@Value("${qingke.filePath}")
    private String filePathConfig;
    @Value("${qingke.fileMapName}")
    private String fileMapNameConfig;
    
    @PostMapping("/upload")
    @ResponseBody
    @ApiOperation("上传文件")
    public RestResult<List<UploadResponse<Map<String,String[]>>>> handleFileUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("file");
        String uploadType = request.getParameter("path");
        Map<String,String[]> data = request.getParameterMap();
        List<UploadResponse<Map<String,String[]>>> res = new ArrayList<UploadResponse<Map<String,String[]>>>();
        MultipartFile file = null;

        BufferedOutputStream stream = null;
        try {
            String folder = (uploadType==null|| uploadType.equals("")?"other":uploadType)+"/";
            String filePath = filePathConfig + folder;
			String urlPath = fileMapNameConfig + "/" +  folder;
			File upload = new File(filePath);
			if (!upload.exists()) {
				upload.mkdirs();
			}
            for (int i = 0; i < files.size(); ++i) {
                file = files.get(i);
                if (!file.isEmpty()) {

                    byte[] bytes = file.getBytes();
                    String originalFilename = file.getOriginalFilename();
                    String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
//                    String path = ResourceUtils.getURL("classpath:").getPath() + "/upload/" + uploadType;
                    String newFilename = UUID.randomUUID().toString() + "." + ext;
                    //File newFile = new File(filePath + newFilename);

                    stream = new BufferedOutputStream(new FileOutputStream(
                            new File(filePath + newFilename)));
                    stream.write(bytes);
                    stream.close();
                    String url = urlPath+newFilename;

                    UploadResponse<Map<String,String[]>> uploadResponse = new UploadResponse<Map<String,String[]>>();
                    uploadResponse.setOriginalFile(originalFilename);
                    uploadResponse.setUrl(url);
                    uploadResponse.setData(data);
                    res.add(uploadResponse);
                } else {
                    return ResultUtil.failed(-1, "You failed to upload " + i
                            + " because the file was empty.", null);
                }

            }
        } catch (Exception e) {
            stream = null;
            return ResultUtil.failed(-1, e.getMessage(), null);

        }
        return ResultUtil.success(res);

    }

    @GetMapping(value = "/get")
    @ResponseBody
    public byte[] getImage(@RequestParam("fileType") String fileType, @RequestParam("filename") String filename) throws Exception {

        String realFilename = ResourceUtils.getURL("classpath:").getPath() + "upload/" + fileType + "/" + filename;
        File file = new File(realFilename);
        if (file.exists()) {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, inputStream.available());
            return bytes;
        }
        else {

            return null;
        }

    }


}
