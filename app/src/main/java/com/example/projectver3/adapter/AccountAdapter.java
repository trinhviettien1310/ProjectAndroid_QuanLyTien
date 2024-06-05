package com.example.projectver3.adapter;

import static com.example.projectver3.fragment.AcountFragment.DocDLAccount;
import static com.example.projectver3.fragment.AcountFragment.DocDLTotal;
import static com.example.projectver3.fragment.AcountFragment.data_account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectver3.R;
import com.example.projectver3.model.Account;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountHolder> {
    private Context context;
    private List<Account> dataAccount;
    private OnItemClickListener onItemClickListener;

    public AccountAdapter(Context context, List<Account> dataAccount, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.dataAccount = dataAccount;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AccountHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountHolder(LayoutInflater.from(context).inflate(R.layout.layout_item_account,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountHolder holder, int position) {
        Account account = dataAccount.get(position);
        holder.bindData(account);
        //click recyclerView
        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemClick(account);
        });
    }

    @Override
    public int getItemCount() {
        return dataAccount.size();
    }
    public interface OnItemClickListener {
        void onItemClick(Account account);
    }
    public void removeItem(int index){
        data_account.child(String.valueOf(index)).removeValue();
        DocDLAccount();
        DocDLTotal();
    }
    public void undoItem(Account account, int index){
        data_account.child(String.valueOf(index)).setValue(account);
        DocDLAccount();
        DocDLTotal();
    }
}
