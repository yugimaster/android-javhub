package com.yugimaster.javhub.bean;

import com.google.gson.annotations.SerializedName;

public class HighPronMovie {

    @SerializedName("MovieId")
    private int movieId;

    @SerializedName("ProductId")
    private String productId;

    @SerializedName("ProductName")
    private String nameProduct;

    @SerializedName("Title")
    private String title;

    @SerializedName("PosterUrl")
    private String posterUrl;

    @SerializedName("Fanart")
    private String fanart;

    @SerializedName("Rate")
    private String rate;

    @SerializedName("Year")
    private int year;

    @SerializedName("Vids")
    private String vidLists;

    @SerializedName("PublishDate")
    private String publishDate;

    @SerializedName("AddedTime")
    private String addedTime;

    @SerializedName("Actors")
    private String actors;

    @SerializedName("Tags")
    private String tags;

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setFanart(String fanart) {
        this.fanart = fanart;
    }

    public String getFanart() {
        return fanart;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRate() {
        return rate;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setVidLists(String vidLists) {
        this.vidLists = vidLists;
    }

    public String getVidLists() {
        return vidLists;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }

    public String getAddedTime() {
        return addedTime;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getActors() {
        return actors;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTags() {
        return tags;
    }
}
