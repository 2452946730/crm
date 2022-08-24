package com.bjpowernode.crm.commons.domain;

/**
 * @Date 2022/8/24 11:41
 */
public class RetObject {
    private String code;
    private String message;

    public RetObject() {
    }

    public RetObject(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RetObject{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
