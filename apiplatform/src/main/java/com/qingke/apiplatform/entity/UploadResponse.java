package com.qingke.apiplatform.entity;

import lombok.Data;

/**
 * @Author 蒋世芳
 * @Date 2018/11/16 4:26 PM
 */
@Data
public class UploadResponse<T> {
    String originalFile;
    String url;
    T data;
}
