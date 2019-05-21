package com.dms.admin.service;

import com.dms.admin.domain.dto.OrgDTO;
import com.dms.admin.domain.param.OrgParam;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author yangchao.
 * @date 2019/4/23 16:21
 */
public interface IOrgService {
    /**
     * 列表查询
     * @param orgParam
     * @return
     */
    Page<OrgDTO> query(OrgParam orgParam);

    /**
     * 创建
     * @param orgParam
     * @return
     */
    OrgDTO create(OrgParam orgParam);

    /**
     * 修改
     * @param orgParam
     * @return
     */
    OrgDTO modify(OrgParam orgParam);

    /**
     * 删除
     * @param orgId
     */
    void remove(Long orgId);

    List<OrgDTO> queryByParentId(Long parentId);

}
