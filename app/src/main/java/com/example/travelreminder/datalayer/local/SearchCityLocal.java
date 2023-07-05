package com.example.travelreminder.datalayer.local;

import android.content.Context;

import com.example.travelreminder.datalayer.ISearchCity;
import com.example.travelreminder.model.CityList;
import com.example.travelreminder.ui.add_trip.OnRecieve;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchCityLocal implements ISearchCity {
    Context context;

    public SearchCityLocal(Context context) {
        this.context = context;
    }

    @Override
    public void searchCity(String city, OnRecieve<String> onRecieve) {
        List<String> cities = new ArrayList<>();
        try {
            cities = CityList.getCityList(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> citiesMatched = new ArrayList<>();
        for(String city1 : cities){
            if(city1.toLowerCase().contains(city.toLowerCase())){
                citiesMatched.add(city1);
            }
        }
        onRecieve.onRecieve(citiesMatched);
    }
}
