package com.example.pay_fee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class HistoryFragment extends Fragment {

    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference transactionRef = db.collection("userTransactions").document(userId).collection("transactions");
    private TransactionAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_history, container, false);

        setUpRecyclerView(root);


        return root;
    }

    private void setUpRecyclerView(View root) {
        Query query = transactionRef;

        FirestoreRecyclerOptions<Transaction> options = new FirestoreRecyclerOptions.Builder<Transaction>()
                .setQuery(query, Transaction.class)
                .build();
        adapter = new TransactionAdapter(options);
        RecyclerView recyclerView = root.findViewById(R.id.transactionHistory);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}