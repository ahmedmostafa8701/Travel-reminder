package com.example.travelreminder.datalayer.remote;

import androidx.lifecycle.LiveData;
import androidx.work.ListenableWorker;

import com.example.travelreminder.datalayer.IDatalayer;

public interface IRemoteDataLayer extends IDatalayer<LiveData<ListenableWorker.Result>> {
}
