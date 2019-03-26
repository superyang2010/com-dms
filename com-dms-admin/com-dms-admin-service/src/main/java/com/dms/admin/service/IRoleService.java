package com.dms.admin.service;

import com.dms.admin.domain.dto.RoleDTO;
import com.dms.admin.domain.param.RoleParam;
import org.springframework.data.domain.Page;

/**
 * @author yangchao.
 * @date 2019/3/7 16:21
 */
public interface IRoleService {
    /**
     * 列表查询
     * @param roleParam
     * @return
     */
    Page<RoleDTO> query(RoleParam roleParam);

    /**
     * 创建
     * @param roleParam
     * @return
     */
    RoleDTO create(RoleParam roleParam);

    /**
     * 修改
     * @param roleParam
     * @return
     */
    RoleDTO modify(RoleParam roleParam);

    /**
     * 删除
     * @param roleParam
     */
    void remove(RoleParam roleParam);
}
