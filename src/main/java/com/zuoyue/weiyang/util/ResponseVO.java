package com.zuoyue.weiyang.util;

import java.io.Serializable;

public class ResponseVO<T> implements Serializable {

    private static final long serialVersionUID = -5007654155748473782L;

    /**
     * 状态吗
     */
    private String code;

    /**
     * 消息（错误信息）
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    public String getCode() {
        return code;
    }

    public ResponseVO<T> setCode(String  code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseVO<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseVO<T> setData(T data) {
        this.data = data;
        return this;
    }

    /**
     * 状态码定义
     */
    public interface ResponseCode {

        /**
         * 成功
         */
        public static final String SUCCESS = "0";

        /**
         * 业务报错
         */
        public static final String ERROR = "1";

        /**
         * 程序异常
         */
        public static final String EXCEPTION = "-1";
    }

    /**
     * 默认创建方法
     * @param <T>
     * @return
     */
    public static <T> ResponseVO<T> build() {
        ResponseVO<T> responseVO = new ResponseVO<>();
        return responseVO.setCode(ResponseCode.SUCCESS);
    }

    /**
     * 默认抛出异常
     * @param <T>
     * @return
     */
    public static <T> ResponseVO<T> exception() {
        ResponseVO<T> responseVO = new ResponseVO<>();
        return responseVO.setCode(ResponseCode.EXCEPTION).setMsg("服务器发生异常，请联系管理员！");
    }

    /**
     * 默认错误
     * @param <T>
     * @return
     */
    public static <T> ResponseVO<T> error() {
        ResponseVO<T> responseVO = new ResponseVO<>();
        return responseVO.setCode(ResponseCode.ERROR);
    }
}
