package com.yugimaster.javhub.view;

public class RowItem {

    private String poster_url;
    private String title;
    private String productId;
    private String tags;
    private String actresses;
    private String movieId;
    private String desc;
    private String playLists;
    private String fanart;

    public RowItem(String poster_url, String title, String productId, String tags, String actresses,
                   String movieId, String desc, String playLists, String fanart) {
        this.poster_url = poster_url;
        this.title = title;
        this.actresses = actresses;
        this.tags = tags;
        this.productId = productId;
        this.movieId = movieId;
        this.desc = desc;
        this.playLists = playLists;
        this.fanart = fanart;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTags() {
        return tags;
    }

    public void setActresses(String actresses) {
        this.actresses = actresses;
    }

    public String getActresses() {
        return actresses;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setPlayLists(String playLists) {
        this.playLists = playLists;
    }

    public String getPlayLists() {
        return playLists;
    }

    public void setFanart(String fanart) {
        this.fanart = fanart;
    }

    public String getFanart() {
        return fanart;
    }
}
