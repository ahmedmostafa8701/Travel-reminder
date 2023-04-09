package com.example.travelreminder.ui.addtrip;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;

import com.example.travelreminder.datalayer.ISearchCity;
import com.example.travelreminder.datalayer.local.SearchCityLocal;

public class CityWatcher implements TextWatcher {
   ArrayAdapter<String> adapter;
   Context context;
   public CityWatcher(Context context, ArrayAdapter<String> adapter) {
      this.adapter = adapter;
      this.context = context;
   }

   @Override
   public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

   }
   @Override
   public void onTextChanged(CharSequence s, int i, int i1, int i2) {
      if(s.length() > 2){
         ISearchCity searchCity = new SearchCityLocal(context);
         searchCity.searchCity(s.toString(), list -> {
            adapter.clear();
            adapter.addAll(list);
            adapter.notifyDataSetChanged();
         });
      }
   }

   @Override
   public void afterTextChanged(Editable editable) {

   }
}
