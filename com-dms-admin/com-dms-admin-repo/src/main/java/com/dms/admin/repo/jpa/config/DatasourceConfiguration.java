package com.dms.admin.repo.jpa.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * 
 * @author superyang
 */
//@Configuration
//@EnableTransactionManagement
public class DatasourceConfiguration implements EnvironmentAware {

    private RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment env) {
        this.propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
    }
    
    @Bean
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(propertyResolver.getProperty("url").trim());
        datasource.setDriverClassName(propertyResolver.getProperty("driverClassName").trim());
        datasource.setUsername(propertyResolver.getProperty("username").trim());
        datasource.setPassword(propertyResolver.getProperty("password").trim());
        datasource.setInitialSize(Integer.valueOf(propertyResolver.getProperty("initialSize").trim()));
        datasource.setMinIdle(Integer.valueOf(propertyResolver.getProperty("minIdle").trim()));
        datasource.setMaxWait(Long.valueOf(propertyResolver.getProperty("maxWait").trim()));
        datasource.setMaxActive(Integer.valueOf(propertyResolver.getProperty("maxActive").trim()));
        return datasource;
    }
}
