package com.jaelyn.chat.common.dto;

/**
 * @author jaelynz@gmail.com
 * @description api结果返回对象
 * @date 2018/12/19 11:22
 **/
public class Result {
    private Object data;
    private int code;
    private String msg;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
