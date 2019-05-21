package com.dms.admin.service;

import com.dms.admin.domain.dto.MenuDTO;
import com.dms.admin.domain.param.MenuParam;
import com.dms.admin.repo.jpa.model.SysMenu;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author yangchao.
 * @date 2019/2/23 16:21
 */
public interface IMenuService {
    /**
     * 列表查询
     * @param menuParam
     * @return
     */
    Page<MenuDTO> query(MenuParam menuParam);

    /**
     * 创建
     * @param menuParam
     * @return
     */
    MenuDTO create(MenuParam menuParam);

    /**
     * 修改
     * @param menuParam
     * @return
     */
    MenuDTO modify(MenuParam menuParam);

    /**
     * 查询
     * @param menuId
     */
    void remove(Long menuId);

    List<MenuDTO> queryByParentId(Long parentId);

    /**
     * 创建
     * @param menuParam
     * @return
     */
    List<SysMenu> save(List<SysMenu> menus);
}
