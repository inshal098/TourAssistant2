package com.tourassistant.coderoids.home.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tourassistant.coderoids.R;

public class ProfileFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        intializeView(v);
        return v;
    }

    private void intializeView(View v) {
        TextView  tvName = v.findViewById(R.id.user_name);
        TextView  tvEmail = v.findViewById(R.id.tv_email);
        FirebaseUser users= FirebaseAuth.getInstance().getCurrentUser();
        if(users.getDisplayName().matches("")){
            tvName.setText("-");
        } else
            tvName.setText(users.getDisplayName());
        tvEmail.setText(users.getEmail());
    }
}