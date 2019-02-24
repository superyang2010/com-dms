/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

/**
 * 
 */
package com.dms.pub.exception;

/**
 * @author yang.chao.
 * @date 2019/2/20
 */
public class ExceptionHandler {

    public static void publish(BusiException e) throws BusiException {
        throw e;
    }

    public static void publish(String errorCode, String errorMsg) throws BusiException {
        publish(errorCode, errorMsg, null);
    }

    public static void publish(String errorCode, String errorMsg, Throwable t) throws BusiException {
        BusiException be;
        if ((t != null) && (t instanceof BusiException)) { 
            be = (BusiException)t;
        } else {
            be = new BusiException(errorCode, errorMsg, t);
        }
        throw be;
    }
}
