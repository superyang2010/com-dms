package com.dms.admin.domain.dto;

import com.dms.admin.repo.jpa.base.BaseModel;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.catalina.User;

import java.util.List;

/**
 * @author yangchao.
 * @date 2019/2/24 14:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDTO extends BaseModel {

    private String name;

    private List<UserDTO> users = Lists.newArrayList();

    private List<MenuDTO> menus = Lists.newArrayList();

    public void addMenu(MenuDTO menu) {
        menus = menus == null ? Lists.newArrayList() : menus;
        menus.add(menu);
    }
}
