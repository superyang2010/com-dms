package com.dms.admin.repo.jpa.dao;

import com.dms.admin.repo.jpa.base.BaseRepository;
import com.dms.admin.repo.jpa.model.SysMenu;
import org.springframework.stereotype.Repository;

/**
 * @author yangchao.
 * @date 2019/2/23 13:05
 */
@Repository
public interface ISysMenuDao extends BaseRepository<SysMenu, Long> {
}
