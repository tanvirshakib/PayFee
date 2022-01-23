package com.example.pay_fee;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AccountActivity extends AppCompatActivity {

    Button buttonCall1,buttonCall2,buttonCall3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        buttonCall1 = findViewById(R.id.button);
        buttonCall2 = findViewById(R.id.button2);
        buttonCall3 = findViewById(R.id.button3);


        buttonCall1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent= new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:01911246885"));
startActivity(intent);

            }
        });
        buttonCall2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent1= new Intent(Intent.ACTION_DIAL);
                intent1.setData(Uri.parse("tel:01968878374"));
                startActivity(intent1);

            }
        });
        buttonCall3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent2= new Intent(Intent.ACTION_DIAL);
                intent2.setData(Uri.parse("tel:01717848188"));
                startActivity(intent2);

            }
        });

    }
}