/**
 * 
 */
package com.dms.pub.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;

/**
 * Controller 基类 
 * @author yang.chao.
 * @date 2019/2/20
 *  
 */
@Slf4j
public class BaseController {

    @Autowired
    protected ServletContext servletContext;
    
    protected static final String LOGIN_ACCOUNT_LOSS = "Session超时，请重新登录";

}
