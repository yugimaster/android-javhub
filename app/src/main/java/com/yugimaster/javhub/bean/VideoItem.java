package com.yugimaster.javhub.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoItem {

    @SerializedName("ProductID")
    private String productId;

    @SerializedName("ActressesJa")
    private List<String> actressesJa;

    @SerializedName("ActressesEn")
    private List<String> getActressesEn;

    @SerializedName("Desc")
    private String desc;

    @SerializedName("MovieID")
    private String movieId;

    @SerializedName("Title")
    private String title;

    @SerializedName("ThumbNail")
    private String poster;

    @SerializedName("Tags")
    private List<String> tags;

    @SerializedName("PlayList")
    private List<Long> playList;

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setActressesJa(List<String> actressesJa) {
        this.actressesJa = actressesJa;
    }

    public List<String> getActressesJa() {
        return actressesJa;
    }

    public void setGetActressesEn(List<String> getActressesEn) {
        this.getActressesEn = getActressesEn;
    }

    public List<String> getGetActressesEn() {
        return getActressesEn;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPoster() {
        return poster;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setPlayList(List<Long> playList) {
        this.playList = playList;
    }

    public List<Long> getPlayList() {
        return playList;
    }
}
