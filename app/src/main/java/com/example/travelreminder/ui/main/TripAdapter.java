package com.example.travelreminder.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelreminder.R;
import com.example.travelreminder.model.Trip;

import java.util.ArrayList;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {

    Context context;
    List<Trip> trips;
    TripMenuItem menuItem;
    StartTrip startTrip;
    public TripAdapter(Context context, TripMenuItem menuItem, StartTrip startTrip) {
        this.context = context;
        trips = new ArrayList<>();
        this.menuItem = menuItem;
        this.startTrip = startTrip;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_list_item, parent, false);
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
        holder.tripImage.setOnClickListener((view)->{
            if(holder.hidden.getVisibility() == View.GONE){
                holder.hidden.setVisibility(View.VISIBLE);
            }
            else if(holder.hidden.getVisibility() == View.VISIBLE){
                holder.hidden.setVisibility(View.GONE);
            }
        });
        holder.tripMenu.setOnClickListener((view) -> menuItem.onItemMenuClick(position));
        holder.startTrip.setOnClickListener((view) -> {
            startTrip.start(trips.get(position));
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
        TextView tripFrom;
        TextView tripTo;
        LinearLayout tripImage;
        ImageView tripMenu;
        ImageButton delete;
        ImageButton edit;
        Button startTrip;
        ConstraintLayout hidden;
        public ViewHolder(@NonNull View view) {
            super(view);
            tripDate = view.findViewById(R.id.trip_date);
            tripTime = view.findViewById(R.id.trip_time_text);
            tripFrom = view.findViewById(R.id.city_from_text);
            tripName = view.findViewById(R.id.trip_name_text);
            tripTo = view.findViewById(R.id.city_to_text);
            tripImage = view.findViewById(R.id.trip_image);
            tripMenu = view.findViewById(R.id.trip_menu);
            delete = view.findViewById(R.id.delete_trip_menu);
            edit = view.findViewById(R.id.edit_trip_menu);
            startTrip = view.findViewById(R.id.start_trip);
            hidden = view.findViewById(R.id.hidden);
        }
    }
}
