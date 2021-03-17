package com.tourassistant.coderoids.home.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tourassistant.coderoids.R;
import com.tourassistant.coderoids.appdb.AppDatabase;
import com.tourassistant.coderoids.home.adapters.TripListAdapter;
import com.tourassistant.coderoids.plantrip.tripdb.TripEntity;

import java.util.List;

public class TripsFragment extends Fragment {
    RecyclerView rvTrips;
    TextView tvEmptyTrip;
    TripListAdapter tripListAdapter;
    LinearLayoutManager llm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trips, container, false);
        initializeView(view);
        getTripListAndView();
        return view;
    }

    private void getTripListAndView() {
        llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        getAllTrips();
    }

    private void initializeView(View view) {
        rvTrips = view.findViewById(R.id.rv_trips);
        tvEmptyTrip = view.findViewById(R.id.emptyTrip);
    }

    private void getAllTrips() {
        try {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    List<TripEntity> tripEntityList = AppDatabase.getAppDatabase(getActivity()).tripDao().getAllTrips();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(tripEntityList.size()>0) {
                                tvEmptyTrip.setVisibility(View.GONE);
                                tripListAdapter = new TripListAdapter(getActivity(), tripEntityList);
                                rvTrips.setAdapter(tripListAdapter);
                                rvTrips.setLayoutManager(llm);
                            } else {
                                tvEmptyTrip.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}