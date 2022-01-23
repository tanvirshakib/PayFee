package com.example.pay_fee;


import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.view.View;

import com.example.pay_fee.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;



public class SignupActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    private EditText idUpdate, nameUpdate, departmentUpdate, emailUpdate;
    private Button buttonUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

      Intent data= getIntent();
        String id= data.getStringExtra("id");

        String name= data.getStringExtra("name");
        String department= data.getStringExtra("department");
        String email= data.getStringExtra("email");


idUpdate= findViewById(R.id.idUpdate);
        nameUpdate= findViewById(R.id.nameUpdate);
        departmentUpdate= findViewById(R.id.departmentUpdate);
        emailUpdate= findViewById(R.id.emailUpdate);

        idUpdate.setText(id);
        nameUpdate.setText(name);
        departmentUpdate.setText(department);
        emailUpdate.setText(email);


        Log.d(TAG, "onCreate"+id+""+name+""+department+""+email);

    }
}
