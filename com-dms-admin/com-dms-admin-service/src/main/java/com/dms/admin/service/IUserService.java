package com.dms.admin.service;

import com.dms.admin.domain.dto.UserDTO;
import com.dms.admin.domain.param.UserParam;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author yangchao.
 * @date 2019/2/23 16:21
 */
public interface IUserService {

    Page<UserDTO> query(UserParam userParam);

    UserDTO create(UserParam userParam);

    UserDTO modify(UserParam userParam);

    void remove(Long userId);
}
