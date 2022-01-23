package com.example.pay_fee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.TextView;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private EditText studentIDInput, nameInput, emailInput, passwordInput,departmentInput;
    private Button buttonRegister;
    private ProgressBar progressbar;
    TextView loginText;

    private FirebaseAuth firebaseAuth;

    public static final String TAG = "YOUR-TAG-NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // taking FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance();


        // initialising all views through id defined above
        studentIDInput = findViewById(R.id.idRegistration);
        nameInput = findViewById(R.id.nameRegistration);
        departmentInput = findViewById(R.id.nameDepartment);
        emailInput = findViewById(R.id.emailRegistration);
        passwordInput = findViewById(R.id.passwordRegistration);
        buttonRegister = findViewById(R.id.buttonRegister);
        progressbar = findViewById(R.id.progressBarRegistration);
        loginText = findViewById(R.id.logintext);


        // Set on Click Listener on Registration button
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                registerNewUser();
            }
        });
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
    }

    private void registerNewUser()
    {

        // show the visibility of progress bar to show loading
        progressbar.setVisibility(View.VISIBLE);

        // Take the value of two edit texts in Strings
        String id, name, email, password, department;
        id = studentIDInput.getText().toString().trim();
        name = nameInput.getText().toString().trim();
        department = departmentInput.getText().toString().trim();
        email = emailInput.getText().toString().trim();
        password = passwordInput.getText().toString().trim();

        // Validations for input email and password
        if (TextUtils.isEmpty(id)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter student id!!",
                    Toast.LENGTH_LONG)
                    .show();

            progressbar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter name!!",
                    Toast.LENGTH_LONG)
                    .show();

            progressbar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(department)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter Department!!",
                    Toast.LENGTH_LONG)
                    .show();

            progressbar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();

            progressbar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();

            progressbar.setVisibility(View.GONE);
            return;
        }

        // create new user or register new user
        firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
// send verification link
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
             Toast.makeText(RegistrationActivity.this,"Verification Email Has Been Sent.",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                               Log.d(TAG, "onFailure:Email Not Sent."+e.getMessage());
                                }
                            });


                            Toast.makeText(getApplicationContext(),
                                    "Registration successful!",
                                    Toast.LENGTH_LONG)
                                    .show();

                            String userId = firebaseAuth.getCurrentUser().getUid();

                            FirebaseFirestore db = FirebaseFirestore.getInstance();

                            Map<String, String> users = new HashMap<>();

                            users.put("id", id);
                            users.put("name", name);
                            users.put("departmentName", department);
                            users.put("universityName", "Leading University");
                            users.put("dateOfBirth", "Not Provided");
                            users.put("picture", "Not Provided");

                            db.collection("users").document(userId).set(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        // hide the progress bar
                                        progressbar.setVisibility(View.GONE);

                                        // if the user created intent to login activity
                                        Intent intent
                                                = new Intent(RegistrationActivity.this,
                                                HomeActivity.class);
                                        startActivity(intent);
                                    }else{
                                        // Registration failed
                                        Toast.makeText(
                                                getApplicationContext(),
                                                "Registration successful!!"
                                                        + " But cannot update profile",
                                                Toast.LENGTH_LONG)
                                                .show();

                                        // hide the progress bar
                                        progressbar.setVisibility(View.GONE);
                                    }
                                }
                            });


                        }
                        else {

                            // Registration failed
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Registration failed!!"
                                            + " Please try again later" +
                                    task.getException().getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress bar
                            progressbar.setVisibility(View.GONE);
                        }
                    }
                });



        
    }
}