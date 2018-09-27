package com.yugimaster.javhub.bean;

import java.util.List;

public class JsonData {

    private List<VideoItem> videos;

    public void setVideos(List videos) {
        this.videos = videos;
    }

    public List getVideos() {
        return videos;
    }
}
