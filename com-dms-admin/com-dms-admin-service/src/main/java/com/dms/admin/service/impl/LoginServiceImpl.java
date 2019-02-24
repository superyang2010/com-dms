package com.dms.admin.service.impl;

import com.dms.admin.domain.dto.LoginDTO;
import com.dms.admin.domain.param.LoginParam;
import com.dms.admin.repo.jpa.dao.ISysUserDao;
import com.dms.admin.repo.jpa.model.SysUser;
import com.dms.admin.service.ILoginService;
import com.dms.pub.enums.StatusEnum;
import com.dms.pub.exception.ExceptionHandler;
import com.dms.pub.util.ObjectUtil;
import com.dms.pub.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 系统登录相关服务
 * @author yangchao.
 * @date 2018/11/23 11:55
 */
@Service
@Slf4j
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private ISysUserDao userDao;

    @Transactional(transactionManager = "transactionManager", readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public LoginDTO login(LoginParam loginParam) {
        String username = loginParam.getUsername();
        String password = loginParam.getPassword();
        log.info("{} 开始登录...", username);
        SysUser user = new SysUser();
        user.setUsername(username);
        user.setStatus(StatusEnum.VALID);
        user.setGmtCreated(null);
        Example<SysUser> query = Example.of(user);
        List<SysUser> users = this.userDao.findAll(query);
        if (CollectionUtils.isEmpty(users)) {
            ExceptionHandler.publish("DMS-LOGIN-00006", "用户名或密码错误");
        }
        user = users.get(0);
        String dbPwd = user.getUserpwd();
        String securityPwd = SecurityUtil.md5Encrypt(username + "/dms/" + password);
        if (StringUtils.isEmpty(dbPwd) || !dbPwd.equals(securityPwd)) {
            ExceptionHandler.publish("DMS-LOGIN-00006", "用户名或密码错误");
        }
        log.info("{} 登录...ok", username);
        LoginDTO loginDTO = ObjectUtil.shallowCopy(user, LoginDTO.class);
        return loginDTO;
    }
}
