package com.dms.admin.repo.jpa.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/** 
 * @ClassName: BaseRepository 
 * @author yangchao
 * @date 2019年2月23日
 * @Description:   
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>,
        JpaSpecificationExecutor<T>, Serializable {
	
}
