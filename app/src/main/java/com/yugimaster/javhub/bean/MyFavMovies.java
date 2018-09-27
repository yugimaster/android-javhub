package com.yugimaster.javhub.bean;

public class MyFavMovies {

    private JsonData data;
    private JsonResult result;

    public void setData(JsonData data) {
        this.data = data;
    }

    public JsonData getData() {
        return data;
    }

    public void setResult(JsonResult result) {
        this.result = result;
    }

    public JsonResult getResult() {
        return result;
    }
}
