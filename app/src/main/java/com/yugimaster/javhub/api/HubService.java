package com.yugimaster.javhub.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HubService {

    @GET("/")
    Single<HubPage> getHubPage();

    @GET("/{link}")
    Single<HubPage> getCategoryPage(@Path("link") String link);
}
