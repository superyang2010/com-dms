package com.dms.admin.service;

import com.dms.admin.domain.dto.UserDTO;
import com.dms.admin.domain.param.UserParam;
import org.springframework.data.domain.Page;

/**
 * @author yangchao.
 * @date 2019/2/23 16:21
 */
public interface IUserService {

    /**
     * 列表查询
     * @param userParam
     * @return
     */
    Page<UserDTO> query(UserParam userParam);

    /**
     * 创建
     * @param userParam
     * @return
     */
    UserDTO create(UserParam userParam);

    /**
     * 修改
     * @param userParam
     * @return
     */
    UserDTO modify(UserParam userParam);

    /**
     * 删除
     * @param userParam
     */
    void remove(UserParam userParam);
}
