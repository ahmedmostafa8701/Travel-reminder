package com.example.travelreminder;

import android.content.Context;
import android.content.Intent;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelreminder.pojo.Trip;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    List<Trip> trips;
    public TripAdapter(Context context, List<Trip> trips) {
        this.context = context;
        this.trips = trips;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.trip_item, parent, false);
        return new TripAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trip trip = trips.get(position);
        holder.statusTextView.setText(trip.getStatus().toString());
        holder.timeTextView.setText(trip.getTime());
        holder.dateTextView.setText(trip.getDate());
        holder.fromTextView.setText(trip.getCityFrom());
        holder.toTextView.setText(trip.getCityTo());
        holder.nameTextView.setText(trip.getName());
    }

    @Override
    public int getItemCount() {
        return FirebaseManager.getInstance().getSize();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView timeTextView;
        TextView nameTextView;
        TextView fromTextView;
        TextView toTextView;
        TextView statusTextView;
        Button start;
        CardView cardView = itemView.findViewById(R.id.card_view);
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.arrival_date_add_trip);
            timeTextView = itemView.findViewById(R.id.arrival_time_add_trip);
            nameTextView = itemView.findViewById(R.id.trip_name_text);
            fromTextView = itemView.findViewById(R.id.city_from_text);
            toTextView = itemView.findViewById(R.id.city_to_text);
            statusTextView = itemView.findViewById(R.id.trip_status_text);
            start = itemView.findViewById(R.id.trip_start_card_item);
            ConstraintLayout hiddenView = itemView.findViewById(R.id.hidden);
            itemView.setOnClickListener(
                    (view)->{
                        if (hiddenView.getVisibility() == View.VISIBLE) {
                            // The transition of the hiddenView is carried out by the TransitionManager class.
                            // Here we use an object of the AutoTransition Class to create a default transition
                            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                            hiddenView.setVisibility(View.GONE);
                        }

                        // If the CardView is not expanded, set its visibility to
                        // visible and change the expand more icon to expand less.
                        else {
                            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                            hiddenView.setVisibility(View.VISIBLE);
                        }
                    }
            );
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
