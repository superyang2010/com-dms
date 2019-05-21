package com.dms.admin.repo.jpa.model;

import com.dms.admin.repo.jpa.base.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

/**
 * @author yangchao.
 * @date 2019/5/23 11:35
 */
@Entity
@Table
@Getter
@Setter
@ToString(callSuper = true)
public class SysParam extends BaseModel {

    @Column(nullable = false, columnDefinition = "json")
    private String value;

    @Column(nullable = false, unique = true)
    private String groupCode;

}
