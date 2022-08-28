package com.bjpowernode.crm.commons.domain;

/**
 * @Date 2022/8/24 11:41
 */
public class RetObject {
    private String code;
    private String message;
    private Object date;

    public RetObject() {
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

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }
}
