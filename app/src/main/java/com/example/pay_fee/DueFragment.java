package com.example.pay_fee;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import java.text.DecimalFormat;


public class DueFragment extends Fragment {

    EditText creditInput, waiverInput, retakeCreditInput, resultDue, previousInput;
    Button fatchDue, button0, button1, button2;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_due, container, false);

        button0 = (Button)v.findViewById(R.id.dueCal);
        button1 = (Button)v.findViewById(R.id.dueEmail);
        button2 = (Button)v.findViewById(R.id.dueAc);

        button0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DueActivity.class);
                startActivity(intent);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SendEmailActivity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AccountActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }
    }
