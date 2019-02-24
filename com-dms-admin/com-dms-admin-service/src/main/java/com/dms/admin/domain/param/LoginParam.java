package com.dms.admin.domain.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yangchao.
 * @date 2018/11/23 11:13
 */
@Data
public class LoginParam implements Serializable {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String captcha;
}
