/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.dms.pub.exception;

import java.io.Serializable;

/**
 * 业务异常类
 * @author yang.chao.
 * @date 2019/2/20
 */
@SuppressWarnings("serial")
public class BusiException extends RuntimeException implements Serializable {
    /** 异常编码 **/
    private String errorCode;
    /** 异常描述 **/
    private String errorMsg;
       
    public BusiException() {
        super();
    }

    public BusiException(String message) {
        this(null, message, null);
    }
    
    public BusiException(int errorCode, String message) {
        this(String.valueOf(errorCode), message, null);
    }

    public BusiException(String errorCode, String message) {
        this(errorCode, message, null);
    }
    
    public BusiException(int errorCode, String message, Throwable cause) {
        this(String.valueOf(errorCode), message, cause);
    }

    public BusiException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMsg = message;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }
    
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (errorCode != null) {
            sb.append("errorCode = [");
            sb.append(errorCode);
            sb.append("]");
            if (errorMsg != null) {
                sb.append("  errorDesc= [");
                sb.append(errorMsg);
                sb.append("]");
            }
        }
        for (int i = 0; i < getStackTrace().length; i++) {
            sb.append("\r\n\tat ");
            sb.append(getStackTrace()[i]);
        }
        Throwable cause = this.getCause();
        while (cause != null) {
            sb.append("\r\nCause by: ");
            sb.append(cause.toString());
            for (int i = 0; i < cause.getStackTrace().length; i++) {
                sb.append("\r\n\tat ");
                sb.append(cause.getStackTrace()[i]);
            }
            cause = cause.getCause();
            if (cause != null) {
                sb.append("\r\nCaused by: ");
            }
        }
        return sb.toString();
    }
}
