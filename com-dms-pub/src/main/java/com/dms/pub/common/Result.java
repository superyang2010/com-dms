/**
 * 
 */
package com.dms.pub.common;

/**
 * 前端响应对象,参考：<a href="https://lark.alipay.com/fe.hemaos/rfc/002">[RFC-002] ajax response 规范</a><br/>
 * a、如果遇到以下 2 种情况，则需要返回 code 和 message：<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;1、请求失败<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;2、请求成功，但是要返回一些提示（警告）信息<br>
 * b、如果有分页，则必须返回分页相关的参数
 *
 * @author yang.chao.
 * @date 2019/2/20
 */
public class Result<T> {
    /**
     * 是否成功,必选
     */
    private Boolean success;
    /**
     * 业务数据,可选
     */
    private T data;
    /**
     * 业务错误码,可选
     */
    private String code;
    /**
     * 详细错误信息,可选
     */
    private String message;
    /**
     * 当前第几页,可选
     */
    private Integer pageIndex;
    /**
     * 每页多少条记录,可选
     */
    private Integer pageSize;
    /**
     * 总共多少页,可选
     */
    private Long pages;
    /**
     * 总共多少条记录,可选
     */
    private Long total;

    public static <T> Result<T> success(String msg) {
        return build(null, msg, true, null, null);
    }

    public static <T> Result<T> success(String msg, T data) {
        return build(null, msg, true, data, null);
    }

    public static <T> Result<T> success(String msg, T data, PageParam pageParam) {
        return build(null, msg, true, data, pageParam);
    }

    public static <T> Result<T> fail(String msg) {
        return build(null, msg, false, null, null);
    }

    public static <T> Result<T> fail(String code, String msg) {
        return build(code, msg, false, null, null);
    }

    private static <T> Result<T> build(String code, String msg, boolean success, T data, PageParam pageParam) {
        return new Result<T>().setSuccess(success).setCode(code).setMessage(msg).setData(data).buildPageParam(pageParam);
    }

    public Result() {

    }

    public Result<T> buildPageParam(PageParam pageParam) {
        if (pageParam != null) {
            this.setPageIndex(pageParam.getPageIndex()).setPageSize(pageParam.getPageSize())
                    .setTotal(pageParam.getTotal()).setPages(pageParam.getPages());
        }
        return this;
    }

    public Boolean isSuccess() {
        return success;
    }

    public Result<T> setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public Result<T> setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Result<T> setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Long getPages() {
        return pages;
    }

    public Result<T> setPages(Long pages) {
        this.pages = pages;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Result<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public Long getTotal() {
        return total;
    }

    public Result<T> setTotal(Long total) {
        this.total = total;
        return this;
    }

}
