package com.yugimaster.javhub.api;

import java.util.List;

import pl.droidsonroids.jspoon.annotation.Selector;

public class HubPage {
    @Selector(".box > ul > li") public List<Movie> movies;
}
