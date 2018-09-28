package com.yugimaster.javhub.bean;

import java.util.List;

public class DataMap {

    private List<HighPronMovie> videos;
    private int total;

    public void setVideos(List<HighPronMovie> videos) {
        this.videos = videos;
    }

    public List<HighPronMovie> getVideos() {
        return videos;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }
}
