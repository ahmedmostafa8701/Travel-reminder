package com.example.travelreminder.ui.add_trip;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.travelreminder.datalayer.ISearchCity;
import com.example.travelreminder.datalayer.local.SearchCityLocal;

public class CityWatcher implements TextWatcher {
   Context context;
   OnRecieve<String> onRecieve;
   public CityWatcher(Context context, OnRecieve<String> onRecieve) {
      this.context = context;
      this.onRecieve = onRecieve;
   }

   @Override
   public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

   }
   @Override
   public void onTextChanged(CharSequence s, int i, int i1, int i2) {
      if(s.length() > 2){
         ISearchCity searchCity = new SearchCityLocal(context);
         searchCity.searchCity(s.toString(), onRecieve);
      }
   }

   @Override
   public void afterTextChanged(Editable editable) {

   }
}
