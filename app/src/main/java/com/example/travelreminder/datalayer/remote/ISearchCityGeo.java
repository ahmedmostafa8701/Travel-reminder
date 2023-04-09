package com.example.travelreminder.datalayer.remote;

import com.example.travelreminder.apis.citysearch.CityRespose;
import com.example.travelreminder.apis.citysearch.GeoDBAPI;
import com.example.travelreminder.datalayer.ISearchCity;
import com.example.travelreminder.model.City;
import com.example.travelreminder.ui.addtrip.OnRecieve;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ISearchCityGeo implements ISearchCity {
    private final String baseUrl = "https://wft-geo-db.p.rapidapi.com/";
    private final String baseUrl2 = "https://raw.githubusercontent.com/";
    private String API_KEY = "2d94a88e9amsh45840cd6d04d98ap1ab7e1jsn685c3abb4455";
    private final String API_HOST = "wft-geo-db.p.rapidapi.com";
    @Override
    public void searchCity(String city, OnRecieve<String> onRecieve) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .addHeader("X-RapidAPI-Key", API_KEY)
                            .addHeader("X-RapidAPI-Host", API_HOST)
                            .build();
                    return chain.proceed(request);
                }).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl2).addConverterFactory(GsonConverterFactory.create()).build();
        GeoDBAPI citiySearch = retrofit.create(GeoDBAPI.class);
        Call<CityRespose> cityRespose = citiySearch.getCities();
        cityRespose.enqueue(new Callback<CityRespose>() {
            @Override
            public void onResponse(Call<CityRespose> call, Response<CityRespose> response) {
                if(response.isSuccessful()){
                    List<String> cityList = new ArrayList<>();
                    for (City city : response.body().getCities()) {
                        cityList.add(city.getName());
                    }
                    onRecieve.onRecieve(cityList);
                }
                else{
                    onRecieve.onRecieve(Arrays.asList("hello"));
                }
            }

            @Override
            public void onFailure(Call<CityRespose> call, Throwable t) {
                onRecieve.onRecieve(Arrays.asList("hello"));
            }
        });
    }
}
