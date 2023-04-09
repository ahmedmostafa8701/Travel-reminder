package com.example.travelreminder.model;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class CityList {

   private static List<String> cities;
   private CityList(){}
   public static List<String> getCityList(Context context) throws IOException {
      if(cities != null){
         return cities;
      }
      AssetManager assetManager = context.getAssets();
      InputStream inputStream = assetManager.open("city_list.json");
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
      StringBuilder jsonString = new StringBuilder();
      String line;
      while ((line = bufferedReader.readLine()) != null) {
         jsonString.append(line);
      }
      Gson gson = new Gson();
      Type type = new TypeToken<TreeMap<String, List<String>>>(){}.getType();
      TreeMap<String, List<String>> treeMap =  gson.fromJson(jsonString.toString(), type);
      List<String> result = new ArrayList<>();
      for(List<String> cities : treeMap.values()){
         result.addAll(cities);
      }
      cities = result;
      return result;
   }
}
