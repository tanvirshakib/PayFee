package com.example.pay_fee;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class TransactionFragment extends Fragment {

    private EditText semesterNameInput, amountInput, bkashNo, bkashPin, cardNameInput, cardNumberInput, cardExpiryDateInput, cardCVCInput;
    private Button submitTransaction;
    private ProgressBar progressBar;

    private RadioGroup radioGroup;

    private String payment = "bkash";


    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_transaction, container, false);
        //final TextView textView = root.findViewById(R.id.text_dashboard);

        semesterNameInput = root.findViewById(R.id.semesterNameInput);
        amountInput = root.findViewById(R.id.amountInput);
        bkashNo = root.findViewById(R.id.bkashNo);
        bkashPin = root.findViewById(R.id.bkashPin);
        cardNameInput = root.findViewById(R.id.cardName);
        cardNumberInput = root.findViewById(R.id.cardNumber);
        cardExpiryDateInput = root.findViewById(R.id.cardExpiryDate);
        cardCVCInput = root.findViewById(R.id.cardCVC);
        submitTransaction = root.findViewById(R.id.submitTransaction);
        progressBar = root.findViewById(R.id.progressBarTransaction);

        radioGroup = root.findViewById(R.id.radioGroup);

        submitTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(payment.equals("bkash")){
                    if(TextUtils.isEmpty(bkashNo.getText().toString().trim())) {
                        bkashNo.setError("Please enter a bkash number");
                        return;
                    }if(TextUtils.isEmpty(bkashPin.getText().toString().trim())) {
                        bkashPin.setError("Please enter a bkash pin");
                        return;
                    }

                }else{
                    if(cardCVCInput.getText().toString().trim().length()>3){
                        cardCVCInput.setError("CVC must be in 3 digit");
                        return;
                    }
                    if (cardExpiryDateInput.getText().toString().trim().length()>5){
                        cardExpiryDateInput.setError("Please write the date according to your card");
                        return;
                    }
                }


                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                dialogBuilder.setCancelable(false);
// ...Irrelevant code for customizing the buttons and title
                dialogBuilder.setTitle("Check all the values");
                dialogBuilder.setMessage("Before submitting please ensure that all values are correct");
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.layout_dialog, null);
                dialogBuilder.setView(dialogView);

                TextView idShow, nameShow, semesterShow, cardNameShow, cardNumberShow, cardExpiryDateShow, totalShow;
                ProgressBar progressBar;

                idShow = dialogView.findViewById(R.id.universityId);
                nameShow = dialogView.findViewById(R.id.name);
                semesterShow = dialogView.findViewById(R.id.semesterName);
                cardNameShow = dialogView.findViewById(R.id.cardNameShow);
                cardNumberShow = dialogView.findViewById(R.id.cardNumberShow);
                cardExpiryDateShow = dialogView.findViewById(R.id.cardExpiryDateShow);
                totalShow = dialogView.findViewById(R.id.amount);
                progressBar = dialogView.findViewById(R.id.progressBarDialog);



                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                Map <String, String> userTransaction = new HashMap<>();

                db.collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                       if (task.isSuccessful()){
                           DocumentSnapshot snapshot = task.getResult();

                           String id = snapshot.get("id").toString();
                           String name = snapshot.get("name").toString();
                           String semesterName = semesterNameInput.getText().toString().trim();

                           String total = amountInput.getText().toString().trim();

                           idShow.setText("ID: "+id);
                           nameShow.setText("Name: "+name);
                           semesterShow.setText("Semester: "+semesterName);

                           totalShow.setText("Total: "+total);

                           progressBar.setVisibility(View.GONE);
                           idShow.setVisibility(View.VISIBLE);
                           nameShow.setVisibility(View.VISIBLE);
                           semesterShow.setVisibility(View.VISIBLE);

                           totalShow.setVisibility(View.VISIBLE);



                           if(payment.equals("bkash")){
                               String bkash = bkashNo.getText().toString().trim();
                               cardNameShow.setText("Bkash No: " +bkash);

                               cardNameShow.setVisibility(View.VISIBLE);

                               userTransaction.put("cardNumber", bkash);

                           }else{
                               String cardName = cardNameInput.getText().toString().trim();
                               String cardNumber = cardNumberInput.getText().toString().trim();
                               String cardExpiryDate = cardExpiryDateInput.getText().toString().trim();

                               cardNameShow.setText("Card Name: " +cardName);
                               cardNumberShow.setText("Card Number: " +cardNumber);
                               cardExpiryDateShow.setText("Card Expiry Date: " +cardExpiryDate);

                               cardNameShow.setVisibility(View.VISIBLE);
                               cardNumberShow.setVisibility(View.VISIBLE);
                               cardExpiryDateShow.setVisibility(View.VISIBLE);


                               userTransaction.put("cardNumber", cardNumber);

                           }




                           userTransaction.put("id", id);
                           userTransaction.put("userId", userId);
                           userTransaction.put("name", name);
                           userTransaction.put("semester", semesterName);

                           userTransaction.put("amount", total);
                       }else {
                           Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                       }




                    }
                });





                dialogBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressBar.setVisibility(View.VISIBLE);
                        db.collection("userTransactions").document(userId).collection("transactions").add(userTransaction).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getContext(), "Transaction Successful", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                    dialog.cancel();
                                }else {
                                    Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    dialog.cancel();
                                }

                            }
                        });
                    }
                });

                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();


            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.bkashButton){
                    payment = "bkash";
                    bkashNo.setVisibility(View.VISIBLE);
                    bkashPin.setVisibility(View.VISIBLE);

                    cardNameInput.setVisibility(View.GONE);
                    cardNumberInput.setVisibility(View.GONE);
                    cardExpiryDateInput.setVisibility(View.GONE);
                    cardCVCInput.setVisibility(View.GONE);
                }else if(checkedId == R.id.visaButton){

                    payment = "card";

                    cardNameInput.setVisibility(View.VISIBLE);
                    cardNumberInput.setVisibility(View.VISIBLE);
                    cardExpiryDateInput.setVisibility(View.VISIBLE);
                    cardCVCInput.setVisibility(View.VISIBLE);

                    bkashNo.setVisibility(View.GONE);
                    bkashPin.setVisibility(View.GONE);
                }
            }
        });

        return root;
    }
}