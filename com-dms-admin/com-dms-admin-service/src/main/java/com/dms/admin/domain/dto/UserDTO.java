package com.dms.admin.domain.dto;

import com.dms.admin.repo.jpa.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yangchao.
 * @date 2019/2/24 14:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseModel {

    private String username;

    private String userpwd;
}
