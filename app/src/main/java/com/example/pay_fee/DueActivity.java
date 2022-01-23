package com.example.pay_fee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.widget.NumberPicker;
import android.view.Menu;


import java.text.DecimalFormat;

public class DueActivity extends AppCompatActivity  {

    EditText creditInput, waiverInput, retakeCreditInput, resultDue, previousInput;
    Button fatchDue, button0, button1, button2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_due);


        final EditText P = (EditText) findViewById(R.id.creditInput);
        final EditText I = (EditText) findViewById(R.id.waiverInput);
        final EditText Y = (EditText) findViewById(R.id.retakeCreditInput);
        final EditText X = (EditText) findViewById(R.id.previousInput);



        Button fatchDue = (Button) findViewById(R.id.fatchDue);




        final TextView resultD = (TextView) findViewById(R.id.resultDue);


        fatchDue.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String st1 = P.getText().toString();
                        String st2 = I.getText().toString();
                        String st3 = Y.getText().toString();
                        String st4 = X.getText().toString();

                        if (TextUtils.isEmpty(st1)) {
                            P.setError("Enter Credit Number");
                            P.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(st2)) {
                            I.setError("Enter Waiver and you dont have to add percent sign here");
                            I.requestFocus();
                            return;
                        }

                        if (TextUtils.isEmpty(st3)) {
                            Y.setError("Enter Retake Credit. If Not Eligible Then Enter 0");
                            Y.requestFocus();
                            return;
                        }

                        if (TextUtils.isEmpty(st4)) {
                            Y.setError("Enter Previously paid amount. If Not Eligible Then Enter 0");
                            Y.requestFocus();
                            return;
                        }


                        float p = Float.parseFloat(st1);
                        float i = Float.parseFloat(st2);
                        float y = Float.parseFloat(st3);
                        float x = Float.parseFloat(st4);

                        float result;


                        float r = (((p * 1900) * (100 - i)) / 100);
                        float r1 = (r + 6000) + y *1900;
                        float r2 = r1 - x;
                        resultD.setText(String.valueOf(r2));
                    }
                });


    }

    }





