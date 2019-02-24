
package com.dms.admin.config;

import com.dms.pub.common.Result;
import com.dms.pub.exception.BusiException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yang.chao.
 * @date 2019/2/20
 */
@RestControllerAdvice
public class GloabalExceptionHandlerConfig {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(GloabalExceptionHandlerConfig.class);
	
	@ExceptionHandler(value = Throwable.class)
    public Result<String> defaultErrorHandler(HttpServletRequest req, Throwable e) throws Exception {
		log.error("There are some errors occured.", e);
		Result<String> respMsg = new Result<String>();
		if(e instanceof BusiException){
			BusiException be = (BusiException)e;
			respMsg.setCode(be.getErrorCode());
			respMsg.setMessage(be.getErrorMsg());
		} else {
			respMsg.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			Throwable throwable = e.getCause();
			String errMsg = throwable.toString();
			while (throwable != null) {
				errMsg = throwable.toString();
				throwable = throwable.getCause();
			}
			respMsg.setMessage(errMsg);
		}
		
        return respMsg;
    }
}
