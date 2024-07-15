package com.example.travelreminder.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelreminder.R;
import com.example.travelreminder.databinding.TripListItemBinding;
import com.example.travelreminder.model.Trip;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TripListItemBinding binding = TripListItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trip trip = trips.get(position);
        holder.binding.setTrip(trip);
        holder.binding.imageView.setOnClickListener((view)->{
            if(holder.binding.hidden.getVisibility() == View.GONE){
                holder.binding.hidden.setVisibility(View.VISIBLE);
            }
            else if(holder.binding.hidden.getVisibility() == View.VISIBLE){
                holder.binding.hidden.setVisibility(View.GONE);
            }
        });
        holder.binding.tripMenu.setOnClickListener((view) -> holder.showMenu(position));
        holder.binding.startTrip.setOnClickListener((view) -> startTrip.start(trips.get(position)));
        holder.itemView.setOnLongClickListener((view) -> {
            holder.showMenu(position);
            return true;
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
        TripListItemBinding binding;

        public ViewHolder(@NonNull TripListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        private void showMenu(int position) {
            PopupMenu popupMenu = new PopupMenu(context,
                    Objects.requireNonNull(binding.tripMenu));
            popupMenu.inflate(R.menu.trip_item_menu);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                popupMenu.setForceShowIcon(true);
            }
            popupMenu.setOnMenuItemClickListener(item -> menuItem.onItemMenuClick(item, position));
            popupMenu.show();
        }
    }
}
