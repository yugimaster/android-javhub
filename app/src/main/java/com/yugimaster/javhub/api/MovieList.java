package com.yugimaster.javhub.api;

import com.yugimaster.javhub.view.RowItem;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.retrofit2.JspoonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class MovieList {

    private static List<RowItem> mRowItemList;

    public static void main(String baseUrl) {
        mRowItemList = new ArrayList<RowItem>();
        HubService hubService = createHubService(baseUrl);
        hubService.getHubPage()
                .subscribe(MovieList::printHubPage);
//        hubService.getVideoPage(2)
//                .subscribe(MovieList::printHubPage);
    }

    public static void categoryPage(String baseUrl, String link) {
        mRowItemList = new ArrayList<RowItem>();
        HubService hubService = createHubService(baseUrl);
        hubService.getCategoryPage(link)
                .subscribe(MovieList::printHubPage);
    }

    public static List<RowItem> getRowItemList() {
        return mRowItemList;
    }

    private static HubService createHubService(String baseUrl) {
        return createRetrofit(baseUrl)
                .create(HubService.class);
    }

    private static Retrofit createRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JspoonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static void printHubPage(HubPage hubPage) {
        hubPage.movies.forEach(MovieList::printMovieList);
    }

    private static void printMovieList(Movie movie) {
        String link = movie.detailLink;
        String url = movie.imageUrl;
        String duration = movie.duration;
        String title = movie.title;
        System.out.println(link);
        System.out.println(url);
        System.out.println(duration);
        System.out.println(title);
        System.out.println();

        RowItem rowItem = new RowItem(url, title, duration, link, "", "", "", "", "");
        mRowItemList.add(rowItem);
    }
}
