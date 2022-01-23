package com.example.pay_fee;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProfileFragment extends Fragment {


    private Button vButton, updateProfile;
    private CardView cardView;
    private ImageView imageView;
    private TextView vMsg,nameView, idView, universityView, departmentView;
    private FirebaseAuth firebaseAuth;

    private ProgressBar progressBar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        //imageView = root.findViewById(R.id.imageProfile);
        nameView = root.findViewById(R.id.nameView);
        idView = root.findViewById(R.id.idView);
        departmentView = root.findViewById(R.id.departmentView);
        universityView = root.findViewById(R.id.universityView);
        progressBar = root.findViewById(R.id.progressBarProfile);
        cardView = root.findViewById(R.id.cardView);
        vButton = root.findViewById(R.id.vButton);
        vMsg = root.findViewById(R.id.vMsg);

        firebaseAuth = FirebaseAuth.getInstance();




        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(!user.isEmailVerified()){
            vMsg.setVisibility(View.VISIBLE);
            vButton.setVisibility(View.VISIBLE);
            vButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(v.getContext(),"Verification Email Has Been Sent.",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("tag", "onFailure:Email Not Sent."+e.getMessage());
                        }
                    });
                }

            });

        }


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    cardView.setVisibility(View.VISIBLE);

                    progressBar.setVisibility(View.GONE);
                    DocumentSnapshot snapshot = task.getResult();

                    String link = snapshot.get("picture").toString();

                    //Picasso.get().load(link).into(imageView);

                    nameView.setText("Name: "+snapshot.get("name"));
                    idView.setText("Student ID: "+snapshot.get("id"));
                    departmentView.setText("Department: "+snapshot.get("departmentName"));
                    universityView.setText("University: "+snapshot.get("universityName"));
                }
            }
        });








        return root;
    }
}