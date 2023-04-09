package com.example.travelreminder.datalayer;

import com.example.travelreminder.ui.addtrip.OnRecieve;

public interface ISearchCity {
  public void searchCity(String city, OnRecieve<String> onRecieve);
}
