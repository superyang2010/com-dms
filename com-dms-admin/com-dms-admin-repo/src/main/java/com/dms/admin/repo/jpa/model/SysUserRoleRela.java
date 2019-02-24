package com.dms.admin.repo.jpa.model;

import com.dms.pub.enums.StatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author yangchao.
 * @date 2019/2/22 21:35
 */
@Entity
@Table
@Setter
@Getter
public class SysUserRoleRela {

    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true)
    private Long id;

    /** 状态:1-有效;0-无效 **/
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable=false)
    private StatusEnum status = StatusEnum.VALID;

    /** 创建时间 **/
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date gmtCreated = new Date();

    /** 修改时间 **/
    @Temporal(TemporalType.TIMESTAMP)
    private Date gmtModified;

    @ManyToOne(cascade= CascadeType.REFRESH, optional = false)
    @JoinColumn(name="role_id", updatable=false)
    private SysRole role;

    @ManyToOne(cascade=CascadeType.REFRESH, optional = false)
    @JoinColumn(name="user_id", updatable=false)
    private SysUser user;

    @Column(name = "is_default", nullable = false, length = 1)
    private boolean hold = false;
}
