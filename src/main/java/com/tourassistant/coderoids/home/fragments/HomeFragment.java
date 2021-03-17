package com.tourassistant.coderoids.home.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.tourassistant.coderoids.R;
import com.tourassistant.coderoids.appdb.AppDatabase;
import com.tourassistant.coderoids.plantrip.PlanTrip;
import com.tourassistant.coderoids.plantrip.tripdb.TripEntity;

import java.util.List;


public class HomeFragment extends Fragment {
    TextView tvTripCount;
    public static HomeFragment instance;
    List<TripEntity> tripDa;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        initializeView(view);
        return view;
    }

    private void initializeView(View view) {
        ExtendedFloatingActionButton floatingActionButton = view.findViewById(R.id.extended_fab);
        tvTripCount = view.findViewById(R.id.trips_count);
        MaterialCardView mCTrip = view.findViewById(R.id.card_trip);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PlanTrip.class));
            }
        });

        mCTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireView()).navigate(R.id.tripsFragment);
            }
        });
        getAllTrips();

    }

    private void getAllTrips() {
        try {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    tripDa = AppDatabase.getAppDatabase(getActivity()).tripDao().getAllTrips();
                    tripDa.size();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvTripCount.setText("Total Trips : "+tripDa.size()+"");
                        }
                    });
                }
            });
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllTrips();
    }

    public void navigateToEditTrip() {
    }
}