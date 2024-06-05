package com.example.projectver3.adapter;

import static com.example.projectver3.Add_Update_TransferActivity.data_userTransfer;
import static com.example.projectver3.TransferHistoryActivity.DocDLTransfer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectver3.R;
import com.example.projectver3.model.Transfer;

import java.util.List;

public class TransferAdapter extends RecyclerView.Adapter<TransferHolder> {
    private Context context;
    private List<Transfer> dataTransfer;
    private OnItemClickListener onItemClickListener;

    public TransferAdapter(Context context, List<Transfer> dataTransfer, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.dataTransfer = dataTransfer;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TransferHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransferHolder(LayoutInflater.from(context).inflate(R.layout.layout_item_transfer,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransferHolder holder, int position) {
        Transfer transfer = dataTransfer.get(position);
        holder.tvFromAccount.setText(transfer.getNameFromAccount());
        holder.tvToAccount.setText(transfer.getNameToAccount());
        holder.tvMoneyTransfer.setText(String.valueOf(transfer.getMoneyTransfer()) + " đ");
        //
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(transfer);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataTransfer.size();
    }


    public interface OnItemClickListener {
        void onItemClick(Transfer transfer);
    }
    public void removeItem(int index){
        data_userTransfer.child(String.valueOf(index)).removeValue();
        DocDLTransfer();
    }
    public void undoItem(Transfer transfer, int index){
        data_userTransfer.child(String.valueOf(index)).setValue(transfer);
        DocDLTransfer();
    }
}
