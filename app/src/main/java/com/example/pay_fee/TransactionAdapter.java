package com.example.pay_fee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class TransactionAdapter extends FirestoreRecyclerAdapter<Transaction, TransactionAdapter.TransactionHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TransactionAdapter(@NonNull FirestoreRecyclerOptions<Transaction> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TransactionAdapter.TransactionHolder holder, int position, @NonNull Transaction model) {
        holder.trxNo.setText("Transaction: "+ (position+1));
        holder.semester.setText("Semester: " + model.semester);
        holder.total.setText("Payment Amount: " + model.amount);

    }

    @NonNull
    @Override
    public TransactionAdapter.TransactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_view,
                parent, false);
        return new TransactionHolder(v);
    }

    class TransactionHolder extends RecyclerView.ViewHolder {

        TextView trxNo, semester, bank, trxID, total;

        public TransactionHolder(View v) {
            super(v);

            trxNo = v.findViewById(R.id.transactionNo);
            semester = v.findViewById(R.id.semester);
            total = v.findViewById(R.id.amount);


        }
    }
}
