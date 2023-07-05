package com.example.travelreminder.datalayer;

import com.example.travelreminder.ui.add_trip.OnRecieve;

public interface ISearchCity {
  public void searchCity(String city, OnRecieve<String> onRecieve);
}
