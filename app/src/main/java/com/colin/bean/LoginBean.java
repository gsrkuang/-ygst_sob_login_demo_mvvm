package com.colin.bean;

/**
 * Date:2022-02-16
 * Time:17:22
 * author:colin
 *
 * {"success":true,"code":10000,"message":"登录成功","data":null}
 */

public class LoginBean {
   boolean success;
   int code;
   String message;
   String data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
