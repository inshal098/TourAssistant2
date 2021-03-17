package com.tourassistant.coderoids.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.tourassistant.coderoids.R;
import com.tourassistant.coderoids.home.fragments.HomeFragment;
import com.tourassistant.coderoids.plantrip.tripdb.TripEntity;

import java.util.List;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.ViewHolder> {
    List<TripEntity> tripData;
    Context context;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTripName , tvStartDate;
        public Button btnEditTrip;
        public ViewHolder(View view) {
            super(view);
            tvTripName = view.findViewById(R.id.tv_tripName);
            tvStartDate = view.findViewById(R.id.tv_trip_start_date);
            btnEditTrip = view.findViewById(R.id.edit_trip);
        }
    }

    public TripListAdapter(FragmentActivity activity, List<TripEntity> tripData) {
        this.tripData = tripData;
        this.context = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_trip, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.tvTripName.setText("Trip Name : "+tripData.get(position).getTripTitle());
        if(tripData.get(position).getStartDate() != null){
            viewHolder.tvStartDate.setText("Starting Date : " + tripData.get(position).getStartDate());
        } else
            viewHolder.tvStartDate.setText("Starting Date : -" );

        viewHolder.btnEditTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("tripId",tripData.get(position).getId());
                intent.putExtra("sec","test");
                Bundle tripBundle = intent.getExtras();

                Navigation.findNavController(v).navigate(R.id.editTripFragment,tripBundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tripData.size();
    }
}
