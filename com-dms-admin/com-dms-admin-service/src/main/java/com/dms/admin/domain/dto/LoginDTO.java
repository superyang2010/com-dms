package com.dms.admin.domain.dto;

import com.dms.admin.repo.jpa.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yangchao.
 * @date 2018/11/23 11:19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginDTO extends BaseModel {

    /**
     * 系统登录用户名
     */
    private String username;
}
