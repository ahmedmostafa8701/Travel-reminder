package com.example.travelreminder.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelreminder.R;
import com.example.travelreminder.pojo.Trip;

import java.util.ArrayList;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {

    Context context;
    List<Trip> trips;

    public TripAdapter(Context context) {
        this.context = context;
        trips = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trip trip = trips.get(position);
        holder.tripDate.setText(trip.getDate());
        holder.tripTime.setText(trip.getTime());
        holder.tripFrom.setText(trip.getCityFrom());
        holder.tripTo.setText(trip.getCityTo());
        holder.tripName.setText(trip.getName());
        holder.tripStatus.setText(trip.getStatus());
        holder.tripImage.setOnClickListener((view)->{
            if(holder.hidden.getVisibility() == View.GONE){
                holder.hidden.setVisibility(View.VISIBLE);
            }
            else if(holder.hidden.getVisibility() == View.VISIBLE){
                holder.hidden.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setTrips(List<Trip> trips){
        this.trips = trips;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tripName;
        TextView tripDate;
        TextView tripTime;
        TextView tripStatus;
        TextView tripFrom;
        TextView tripTo;
        ImageView tripImage;
        ConstraintLayout hidden;
        public ViewHolder(@NonNull View view) {
            super(view);
            tripDate = view.findViewById(R.id.trip_date);
            tripTime = view.findViewById(R.id.trip_time_text);
            tripFrom = view.findViewById(R.id.city_from_text);
            tripName = view.findViewById(R.id.trip_name_text);
            tripTo = view.findViewById(R.id.city_to_text);
            tripStatus = view.findViewById(R.id.trip_status_text);
            tripImage = view.findViewById(R.id.trip_image);
            hidden = view.findViewById(R.id.hidden);
        }
    }
}
