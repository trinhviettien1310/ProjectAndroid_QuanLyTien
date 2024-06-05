package com.example.projectver3.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectver3.Add_Update_Remind;
import com.example.projectver3.R;
import com.example.projectver3.adapter.RemindAdapter;
import com.example.projectver3.login.LoginActivity;
import com.example.projectver3.model.Remind;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class RemindFragment extends Fragment {
    private static final int MY_REQUEST_CODE = 10;
    Button btnCreateRemind,btnGetData;
    RecyclerView revDanhSach;
    public static RemindAdapter remindAdapter;
    Switch swOnOff;
    public static FirebaseDatabase database_Remind;
    public static DatabaseReference dataRemind;

    public static ArrayList<Remind> data_Remind = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_remind, container, false);
        btnCreateRemind = view.findViewById(R.id.btnAddRemind);
        revDanhSach = view.findViewById(R.id.revDanhSach);
        revDanhSach.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        remindAdapter = new RemindAdapter(getContext(), data_Remind, new RemindAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Remind remind) {
                Intent intent = new Intent(getContext(), Add_Update_Remind.class);
                intent.putExtra("isUpdate", true);
                Bundle bundle = new Bundle();
                bundle.putSerializable("remindUpdate", remind);
                intent.putExtras(bundle);
                startActivityForResult(intent, MY_REQUEST_CODE);
            }
        });
        revDanhSach.setAdapter(remindAdapter);
        //Firebase
        database_Remind = FirebaseDatabase.getInstance();
        dataRemind = database_Remind.getReference("Remind");
        //
        btnCreateRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Add_Update_Remind.class);
                startActivity(intent);
            }
        });
        dataRemind.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DocDL();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DocDL();
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
        return view;
    }
    public static void DocDL() {
        dataRemind.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data_Remind.clear();
                //Toast.makeText(ge, "thay đổi", Toast.LENGTH_SHORT).show();
                for (DataSnapshot item : snapshot.getChildren() ){
                    Remind r = item.getValue(Remind.class);
                    if (r.getMailRemind().equals(LoginActivity.currenEmailUser)){
                        data_Remind.add(r);
                    }
                }
                remindAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}