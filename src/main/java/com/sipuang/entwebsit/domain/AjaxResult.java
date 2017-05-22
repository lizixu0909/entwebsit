package com.sipuang.entwebsit.domain;


/**
 * Created by lj on 2017/3/28.
 */
public class AjaxResult<T> {

    /**
     * 返回状态编码
     */
    private Integer code;
    /**
     * 返回错误消息
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;

    public AjaxResult() {
        this.code = ResultStatus.SUCCESS.getCode();
        this.msg = ResultStatus.SUCCESS.getMsg();
        this.data = null;
    }

    public AjaxResult(T data) {
        this.code = ResultStatus.SUCCESS.getCode();
        this.msg = ResultStatus.SUCCESS.getMsg();
        this.data = data;
    }

    public AjaxResult(ResultStatus status) {
        this.code = status.getCode();
        this.msg = status.getMsg();
        this.data = null;
    }

    public AjaxResult(ResultStatus status, T data) {
        this.code = status.getCode();
        this.msg = status.getMsg();
        this.data = data;
    }

    public static <T> AjaxResult SUCCESS(T data) {
        return new AjaxResult(data);
    }

    public static AjaxResult ERROR(ResultStatus status) {
        return new AjaxResult(status);
    }

    public static AjaxResult SERVICEERROR(String msg) {
        AjaxResult ajaxResult = new AjaxResult(ResultStatus.CUSTOM_ERROR);
        ajaxResult.setMsg(msg);
        return ajaxResult;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\":" + code +
                ", \"msg\":\"" + msg + "\"" +
                ", \"data\":" + data +
                '}';
    }

    public enum ResultStatus {
        /**
         * 成功
         */
        SUCCESS(0, "success"),
        /**
         * 未登录
         */
        NOTLOGIN_ERROR(-1, "用户未登录"),
        /**
         * 发生了错误， 但并非异常， 比如登录失败， 或者流程时操作没有权限，属于业务上的错误
         */
        CUSTOM_ERROR(-2, "业务错误，自定义错误信息"),
        /**
         * 错误请求
         */
        BAD_REQUEST(400, "错误的请求参数"),
        /**
         * 无访问权限
         */
        METHOD_NOT_ALLOWED(405, "无权限访问"),
        /**
         * 服务器内部错误
         */
        INTERNAL_SERVER_ERROR(500, "服务器内部错误");
        private Integer code;
        private String msg;

        ResultStatus(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        @Override
        public String toString() {
            return "{\"code\":" + code + ",\"msg\":\"" + msg + "\"}";
        }
    }
}
