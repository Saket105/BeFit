package com.example.befit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.befit.Activities.LoginActivity;
import com.example.befit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    Button logout;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        logout = root.findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        return root;
    }

    private void logout() {
        mAuth.signOut();
        getActivity().finish();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        Toast.makeText(getContext(), "Logged Out!", Toast.LENGTH_SHORT).show();
    }
}