package com.dms.admin.service;

import com.dms.admin.domain.dto.ParamDTO;
import com.dms.admin.domain.param.ParamParam;
import org.springframework.data.domain.Page;

/**
 * @author yangchao.
 * @date 2019/4/23 16:21
 */
public interface IParamService {
    /**
     * 列表查询
     * @param paramParam
     * @return
     */
    Page<ParamDTO> query(ParamParam paramParam);

    /**
     * 创建
     * @param paramParam
     * @return
     */
    ParamDTO create(ParamParam paramParam);

    /**
     * 修改
     * @param paramParam
     * @return
     */
    ParamDTO modify(ParamParam paramParam);

    /**
     * 删除
     * @param paramId
     */
    void remove(Long paramId);

    /**
     * 删除
     * @param paramCode
     */
    void remove(String paramCode);

}
