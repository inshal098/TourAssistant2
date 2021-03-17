package com.tourassistant.coderoids.plantrip;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.tourassistant.coderoids.R;
import com.tourassistant.coderoids.appdb.AppDatabase;
import com.tourassistant.coderoids.appdb.DatabaseClient;
import com.tourassistant.coderoids.plantrip.tripdb.TripEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlanTrip extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_trip);
        ImageButton ibCross = findViewById(R.id.ib_cross);
        Button ibCreateTrip = findViewById(R.id.create_trip);
        TextInputEditText etTripName = findViewById(R.id.et_name_trip);
        ibCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ibCreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tripName = etTripName.getText().toString();
                if (TextUtils.isEmpty(tripName)) {
                    Toast.makeText(PlanTrip.this, "You Must Enter a Trip Name", Toast.LENGTH_SHORT).show();
                } else {
                    saveTrip(tripName);
                    finish();
                }
            }
        });
    }

    private void saveTrip(String tripName) {
        try {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    TripEntity tripEntity = new TripEntity();
                    tripEntity.setTripTitle(tripName);
                    AppDatabase.getAppDatabase(getApplicationContext()).tripDao().insertTrip(tripEntity);

                }
            });
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

    }
}