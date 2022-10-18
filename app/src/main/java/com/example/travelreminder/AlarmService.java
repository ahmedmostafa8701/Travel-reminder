package com.example.travelreminder;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class AlarmService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
