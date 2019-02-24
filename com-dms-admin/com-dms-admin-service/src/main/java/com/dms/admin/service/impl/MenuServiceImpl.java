package com.dms.admin.service.impl;

import com.dms.admin.repo.jpa.dao.ISysMenuDao;
import com.dms.admin.service.IMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yangchao.
 * @date 2019/2/23 16:31
 */
@Service
@Slf4j
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private ISysMenuDao menuDao;

}
