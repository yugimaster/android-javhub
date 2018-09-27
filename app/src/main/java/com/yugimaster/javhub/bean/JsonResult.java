package com.yugimaster.javhub.bean;

public class JsonResult {

    private String message;
    private Long ret;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setRet(Long ret) {
        this.ret = ret;
    }

    public Long getRet() {
        return ret;
    }
}
