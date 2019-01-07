package com.qingke.apiplatform.entity;

import lombok.*;

/**
 * @Author 蒋世芳【jiangshifang@reignwood.com】
 * @Date 2018/11/8 5:11 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    String Id;
    String username;
    String password;
    
    public String getUsername() {
    	return username;
    }
}