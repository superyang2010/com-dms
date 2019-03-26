package com.dms.admin.repo.jpa.dao;

import com.dms.admin.repo.jpa.base.BaseRepository;
import com.dms.admin.repo.jpa.model.SysMenu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangchao.
 * @date 2019/2/23 13:05
 */
@Repository
public interface ISysMenuDao extends BaseRepository<SysMenu, Long> {

    @Query("SELECT DISTINCT menu FROM SysMenu menu LEFT JOIN FETCH menu.children WHERE menu.parentMenu.id is null")
    List<SysMenu> findALLWithChildren();

    @Query("SELECT DISTINCT menu FROM SysMenu menu LEFT JOIN FETCH menu.children WHERE menu.parentMenu.id=?1")
    List<SysMenu> findMenuWithChildrenByParentId(Long parentMenuId);

    @Query("SELECT DISTINCT menu FROM SysMenu menu LEFT JOIN FETCH menu.children WHERE menu.id=?1")
    List<SysMenu> findMenuWithChildrenById(Long parentMenuId);
}
