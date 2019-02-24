package com.dms.admin.repo.jpa.model;

import com.dms.pub.enums.StatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

/**
 * @author yangchao.
 * @date 2019/2/22 21:35
 */
@Entity
@Table
@Data
public class SysRoleMenuRela {

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

    @ManyToOne(cascade= CascadeType.REFRESH)
    @JoinColumn(name="role_id", updatable=false)
    private SysRole role;

    @ManyToOne(cascade=CascadeType.REFRESH)
    @JoinColumn(name="menu_id", updatable=false)
    private SysMenu menu;

}
