package com.dms.admin.repo.jpa.dao;

import com.dms.admin.repo.jpa.base.BaseRepository;
import com.dms.admin.repo.jpa.model.SysMenu;
import com.dms.admin.repo.jpa.model.SysOrg;
import com.dms.admin.repo.jpa.model.SysParam;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangchao.
 * @date 2019/5/23 13:05
 */
@Repository
public interface ISysParamDao extends BaseRepository<SysParam, Long> {

}
