package com.tourassistant.coderoids.home.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tourassistant.coderoids.R;
import com.tourassistant.coderoids.appdb.AppDatabase;
import com.tourassistant.coderoids.plantrip.tripdb.TripEntity;

import java.util.Calendar;

public class EditTripFragment extends Fragment{
    TextInputEditText tvTripName,tvTextDesctiption,tvStartDate,tvEndDate,tvBudget,etDestinaation;
    MaterialButton btnSave;
    Object id= "";
    ProgressDialog dialog;
    DatePickerDialog datePickerDialog;
    SwitchMaterial swTripState;
    ImageButton ibDeleteTrip;
    int tag = -1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_trip, container, false);
        if(getArguments() != null)
            id = getArguments().get("tripId");
        datePickerDialog = new DatePickerDialog(
                getActivity());
        initializeView(view);
        return view;
    }

    private void initializeView(View view) {
        tvTripName = view.findViewById(R.id.et_trip_name);
        tvTextDesctiption = view.findViewById(R.id.et_trip_description);
        etDestinaation = view.findViewById(R.id.et_destination);
        tvStartDate = view.findViewById(R.id.et_start_date);
        swTripState = view.findViewById(R.id.sw_trip_state);
        TextInputLayout tvInLayout = view.findViewById(R.id.tl_starte_date);
        TextInputLayout tvEndDateL = view.findViewById(R.id.tl_end_date);
        tvEndDate = view.findViewById(R.id.et_end_date);
        tvBudget = view.findViewById(R.id.et_trip_budget);
        ibDeleteTrip = view.findViewById(R.id.delete_trip);
        btnSave = view.findViewById(R.id.btn_save);
        tvInLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = 0;
                datePickerDialog.show();
            }
        });

        tvEndDateL.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = 1;
                datePickerDialog.show();
            }
        });

        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String months = "";
                if(month <10){
                    months = "0"+month;
                } else
                    months =month+"";
                if(tag == 0){
                    tvStartDate.setText(dayOfMonth + "-" + months +"-"+year);
                } else if(tag == 1){
                    tvEndDate.setText(dayOfMonth + "-" + months +"-"+year);
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTripDetails();
            }
        });

        ibDeleteTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        TripEntity tripEntity = new TripEntity();
                        tripEntity.setId((Integer) id);
                        AppDatabase.getAppDatabase(getActivity()).tripDao().deleteTrip(tripEntity);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.setMessage("Deleting...");
                                dialog.setIndeterminate(true);
                                dialog.show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //Do something after 100ms
                                        dialog.dismiss();
                                        Navigation.findNavController(requireView()).navigate(R.id.tripsFragment);

                                    }
                                }, 2000);

                            }
                        });

                    }

                });
            }
        });
        dialog = new ProgressDialog(getActivity());
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                TripEntity tripEntity = AppDatabase.getAppDatabase(getActivity()).tripDao().getTripById((Integer) id);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTripName.setText(tripEntity.getTripTitle());
                        if (tripEntity.getTripDescription() != null)
                            tvTextDesctiption.setText(tripEntity.getTripDescription());
                        if (tripEntity.getStartDate() != null)
                            tvStartDate.setText(tripEntity.getStartDate());
                        if (tripEntity.getEndDate() != null)
                            tvEndDate.setText(tripEntity.getEndDate());
                        if (tripEntity.getTripBudget() != null)
                            tvBudget.setText(tripEntity.getTripBudget());
                        if(tripEntity.getDestination() != null){
                            etDestinaation.setText(tripEntity.getDestination());
                        }
                    }
                });

            }

        });

    }

    private void saveTripDetails() {
        dialog.setMessage("Updating...");
        dialog.setIndeterminate(true);
        dialog.show();
        TripEntity tripEntity = new TripEntity();
        tripEntity.setTripTitle(tvTripName.getText().toString());
        tripEntity.setTripDescription(tvTextDesctiption.getText().toString());
        tripEntity.setStartDate(tvStartDate.getText().toString());
        tripEntity.setEndDate(tvEndDate.getText().toString());
        tripEntity.setTripBudget(tvBudget.getText().toString());
        tripEntity.setDestination(etDestinaation.getText().toString());
        if(swTripState.isChecked()){
            tripEntity.setIsPrivate("1");
        } else
            tripEntity.setIsPrivate("0");
        tripEntity.setId((Integer) id);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase.getAppDatabase(getActivity()).tripDao().updateTrip(tripEntity);
                dialog.dismiss();
            }
        });
    }

}