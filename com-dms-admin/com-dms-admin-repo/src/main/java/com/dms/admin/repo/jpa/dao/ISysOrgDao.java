package com.dms.admin.repo.jpa.dao;

import com.dms.admin.repo.jpa.base.BaseRepository;
import com.dms.admin.repo.jpa.model.SysMenu;
import com.dms.admin.repo.jpa.model.SysOrg;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangchao.
 * @date 2019/5/23 13:05
 */
@Repository
public interface ISysOrgDao extends BaseRepository<SysOrg, Long> {

    @Query("SELECT DISTINCT org FROM SysOrg org LEFT JOIN FETCH org.children WHERE org.parentOrg.id is null ORDER BY org.id ASC")
    List<SysMenu> findALLWithChildren();

    @Query("SELECT DISTINCT org FROM SysOrg org LEFT JOIN FETCH org.children WHERE org.parentOrg.id=?1  ORDER BY org.id ASC")
    List<SysMenu> findMenuWithChildrenByParentId(Long parentMenuId);

    @Query("SELECT DISTINCT org FROM SysOrg org LEFT JOIN FETCH org.children WHERE org.id=?1  ORDER BY org.id")
    List<SysMenu> findMenuWithChildrenById(Long parentMenuId);
}
