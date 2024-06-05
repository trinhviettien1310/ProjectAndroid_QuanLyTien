package com.example.projectver3;



import static com.example.projectver3.Add_Update_TransferActivity.data_userTransfer;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.projectver3.adapter.TransferAdapter;
import com.example.projectver3.adapter.TransferHolder;
import com.example.projectver3.fragment.AcountFragment;
import com.example.projectver3.model.Transfer;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TransferHistoryActivity extends AppCompatActivity implements ItemTouchHelperListener{
    private static final int MY_REQUEST_CODE = 10;
    Button btnBackTransferHistory;
    Button btnAddTransfer;
    RecyclerView revDanhSachTransfer;
    public static ArrayList<Transfer> data_transfer = new ArrayList<>();
    public static TransferAdapter transferAdapter;
    public static FirebaseDatabase databaseTransfer;
//    public static DatabaseReference data_userTransfer;
    RelativeLayout rootViewTransfer;
    SwipeRefreshLayout swiperefreshlayoutTransfer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferhistory);
        setControl();
        setEvent();
    }

    private void setControl() {
        btnBackTransferHistory = findViewById(R.id.btnBackTransferHistory);
        revDanhSachTransfer = findViewById(R.id.revDanhSachTransfer);
        btnAddTransfer = findViewById(R.id.btnAddTransfer);
        rootViewTransfer = findViewById(R.id.root_viewTransfer);
        swiperefreshlayoutTransfer = findViewById(R.id.swiperefreshlayoutTransfer);

    }

    private void setEvent() {

        //khoiTao();
        revDanhSachTransfer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        transferAdapter = new TransferAdapter(this, data_transfer, new TransferAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Transfer transfer) {
                Intent intent = new Intent(TransferHistoryActivity.this, TransferDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("detailsTransfer",transfer);
                intent.putExtras(bundle);
                startActivityForResult(intent,MY_REQUEST_CODE);
            }
        });
        revDanhSachTransfer.setAdapter(transferAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        revDanhSachTransfer.addItemDecoration(itemDecoration);
        //Firebase
        databaseTransfer = FirebaseDatabase.getInstance();
        data_userTransfer = databaseTransfer.getReference("Transfer");
        //Swiped
        ItemTouchHelper.SimpleCallback simpleCallback = new RecycelViewItemTouchHelperTransfer(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(revDanhSachTransfer);
        swiperefreshlayoutTransfer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DocDLTransfer();
                swiperefreshlayoutTransfer.setRefreshing(false);
            }
        });
        //
        btnBackTransferHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(TransferHistoryActivity.this, AcountFragment.class);
//                startActivity(intent);
                finish();
            }
        });
        btnAddTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocDLTransfer();
            }
        });
        data_userTransfer.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DocDLTransfer();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DocDLTransfer();
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
    }
    public static void DocDLTransfer() {

        data_userTransfer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data_transfer.clear();
                //Toast.makeText(ge, "thay đổi", Toast.LENGTH_SHORT).show();
                for (DataSnapshot item : snapshot.getChildren()) {
                    Transfer r = item.getValue(Transfer.class);
                    data_transfer.add(r);
                }
                transferAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void khoiTao() {
        //data_transfer.add(new Transfer("1",new Account(""),"Do an","10/10/2013","123",1));
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof TransferHolder){
            String nameTransferDelete = data_transfer.get(viewHolder.getAdapterPosition()).getNameFromAccount();
            Transfer transfertDelete = data_transfer.get(viewHolder.getAdapterPosition());
            int indexDelete = data_transfer.get(viewHolder.getAdapterPosition()).getIdTransfer();

            //Remove item
            transferAdapter.removeItem(indexDelete);
            Snackbar snackbar = Snackbar.make(rootViewTransfer,nameTransferDelete + "remove!",Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    transferAdapter.undoItem(transfertDelete,indexDelete);
                }
            });
            snackbar.setActionTextColor(Color.GREEN);
            snackbar.show();
        }
    }
}