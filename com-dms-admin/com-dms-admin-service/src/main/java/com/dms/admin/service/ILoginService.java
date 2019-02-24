package com.dms.admin.service;

import com.dms.admin.domain.dto.LoginDTO;
import com.dms.admin.domain.param.LoginParam;

/**
 * @author yangchao.
 * @date 2018/11/23 11:54
 */
public interface ILoginService {

    /**
     * 登录校验
     * @param loginParam
     * @return
     */
    LoginDTO login(LoginParam loginParam);
}
