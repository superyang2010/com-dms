package com.dms.admin.repo.jpa.dao;

import com.dms.admin.repo.jpa.base.BaseRepository;
import com.dms.admin.repo.jpa.model.SysUser;
import org.springframework.stereotype.Repository;

/**
 * @author yangchao.
 * @date 2019/2/23 13:05
 */
@Repository
public interface ISysUserDao extends BaseRepository<SysUser, Long> {
}
