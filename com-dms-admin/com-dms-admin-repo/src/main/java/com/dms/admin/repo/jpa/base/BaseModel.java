package com.dms.admin.repo.jpa.base;

import com.dms.pub.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <b>Summary: </b>
 *      模型对象基类
 * <b>Remarks: </b>
 *        模型对象基类
 */
@MappedSuperclass
@SuppressWarnings("serial")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"}) 
@JsonInclude(value=Include.NON_NULL)
@Data
public abstract class BaseModel implements Serializable {

	@Id
	@GeneratedValue
	@Column(nullable = false, unique = true)
	protected Long id;

    /** 编码 **/
    @Column(nullable = false, unique = true, length = 32)
    protected String code;

	@Enumerated(EnumType.STRING)
	@Column(nullable=false, length = 8)
    protected StatusEnum status = StatusEnum.Y;

	/** 描述 **/
    @Column(length = 1024)
    protected String notes;

	/** 创建时间 **/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
    protected Date gmtCreated = new Date();

	/** 修改时间 **/
	@Temporal(TemporalType.TIMESTAMP)
    protected Date gmtModified;

}


