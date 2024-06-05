package com.example.projectver3.fragment;

import static com.example.projectver3.Add_Update_AccountActivity.account;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.projectver3.Add_Update_AccountActivity;
import com.example.projectver3.Add_Update_TransferActivity;
import com.example.projectver3.ItemTouchHelperListener;
import com.example.projectver3.R;
import com.example.projectver3.RecycelViewItemTouchHelperAccount;
import com.example.projectver3.TransferHistoryActivity;
import com.example.projectver3.adapter.AccountAdapter;
import com.example.projectver3.adapter.AccountHolder;
import com.example.projectver3.login.LoginActivity;
import com.example.projectver3.model.Account;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AcountFragment extends Fragment implements ItemTouchHelperListener {
    private static final int MY_REQUEST_CODE = 10;
    static TextView tvMoneyAcount;
    Button btnTransferHistory,btnTransaction,btnCreateAccount;
    RecyclerView revDanhSachAccount;
    public static AccountAdapter accountAdapter;
    public static ArrayList<Account> data_Account = new ArrayList<>();
    public static FirebaseDatabase database_account;
    public static DatabaseReference data_account;
    public static FirebaseDatabase databaseTotal;
    public static DatabaseReference data_total;

    RelativeLayout rootView;
    SwipeRefreshLayout swipeRefreshLayout;
    SensorManager sensorManager;
    Sensor proximitySensor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accout, container, false);
        btnTransferHistory = view.findViewById(R.id.btnTransferHistory);
        btnTransaction = view.findViewById(R.id.btnTransaction);
        btnCreateAccount = view.findViewById(R.id.btnCreateAccount);
        revDanhSachAccount = view.findViewById(R.id.revDanhSachAccount);
        tvMoneyAcount = view.findViewById(R.id.tvMoneyAcount);
        rootView = view.findViewById(R.id.root_view);
        swipeRefreshLayout =  view.findViewById(R.id.swiperefreshlayout);


        revDanhSachAccount.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        accountAdapter = new AccountAdapter(getContext(), data_Account, new AccountAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Account account) {
                Intent intent = new Intent(getContext(), Add_Update_AccountActivity.class);
                intent.putExtra("isUpdateAccount",true);
                Bundle bundle = new Bundle();
                bundle.putSerializable("accountUpdate",account);
                intent.putExtras(bundle);
                startActivityForResult(intent,MY_REQUEST_CODE);
            }
        });
        revDanhSachAccount.setAdapter(accountAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        revDanhSachAccount.addItemDecoration(itemDecoration);

        //Firebase
        database_account = FirebaseDatabase.getInstance();
        data_account = database_account.getReference("Account");
        //
        //Firebase
        databaseTotal = FirebaseDatabase.getInstance();
        data_total = databaseTotal.getReference("Total");
        //swipe
        ItemTouchHelper.SimpleCallback simpleCallback = new RecycelViewItemTouchHelperAccount(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(revDanhSachAccount);
        btnTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Add_Update_TransferActivity.class);
                startActivity(intent);
            }
        });
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Add_Update_AccountActivity.class);
                startActivity(intent);
            }
        });
        btnTransferHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DocDLTransfer();
                Intent intent = new Intent(getContext(), TransferHistoryActivity.class);
                startActivity(intent);
            }
        });
        data_account.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DocDLAccount();
                DocDLTotal();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DocDLTotal();
                DocDLAccount();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DocDLAccount();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }
    private static void totalMoney(){
        // Tính tổng tiền sau khi dữ liệu đã được tải hoàn toàn
        int totalMoney = 0;
        for (Account account1 : data_Account) {
            totalMoney += account1.getMoneyAccount();
        }
        // Hiển thị tổng tiền và cập nhật Firebase (nếu cần)

        if (account != null){
            account.setTotalMoney(totalMoney);
        }
        tvMoneyAcount.setText(account.getTotalMoney() + " đ");
//        Log.d("AccountDebug", "account: " + account.getTotalMoney()); // Kiểm tra giá trị của account
//        Log.d("AccountDebug", "totalMoney: " + totalMoney); // Kiểm tra giá trị của totalMoney
        data_total.child("total").setValue(account);

    }
    public static void DocDLAccount(){
        data_account.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data_Account.clear();
                for (DataSnapshot item : snapshot.getChildren()){
                    Account a = item.getValue(Account.class);
                    if(a.geteMail().equals(LoginActivity.currenEmailUser)){
                        data_Account.add(a);
                    }
                }
                accountAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
    public static void DocDLTotal(){
        data_total.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalMoney();
                accountAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof AccountHolder){
            String nameAccountDelete = data_Account.get(viewHolder.getAdapterPosition()).getNameAccount();
            Account accountDelete = data_Account.get(viewHolder.getAdapterPosition());
            int indexDelete = data_Account.get(viewHolder.getAdapterPosition()).getIdAccount();
            //Remove item
            accountAdapter.removeItem(indexDelete);
            Snackbar snackbar = Snackbar.make(rootView,nameAccountDelete + " remove!",Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    accountAdapter.undoItem(accountDelete,indexDelete);
                }
            });
            snackbar.setActionTextColor(Color.GREEN);
            snackbar.show();
        }
    }
}
