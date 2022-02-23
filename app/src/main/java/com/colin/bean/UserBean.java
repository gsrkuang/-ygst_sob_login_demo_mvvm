package com.colin.bean;

/**
 * Date:2022-02-17
 * Time:13:08
 * author:colin
 * 用于登陆接口传入的bean
 */
public class UserBean {

    String phoneNum;
    String password;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "phoneNum='" + phoneNum + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
