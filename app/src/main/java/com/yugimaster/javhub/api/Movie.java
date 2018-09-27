package com.yugimaster.javhub.api;

import pl.droidsonroids.jspoon.annotation.Selector;

public class Movie {
    @Selector(value = "a", attr = "href") public String detailLink;
    @Selector(value = "a > img", attr = "src") public String imageUrl;
    @Selector("a > h3") public String title;
    @Selector("a > span") public String duration;
}
