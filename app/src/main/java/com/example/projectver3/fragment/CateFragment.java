package com.example.projectver3.fragment;

import static android.app.Activity.RESULT_OK;

import static com.example.projectver3.adapter.DanhMucAdapter.dsEx;
import static com.example.projectver3.adapter.DanhMucAdapter.dsIn;
import static com.example.projectver3.fragment.DanhMucExpenseFragment.adapterEx;
import static com.example.projectver3.fragment.DanhMucIncomeFragment.adapterIn;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.projectver3.R;
import com.example.projectver3.adapter.DanhMucAdapter;
import com.example.projectver3.adapter.ViewPageDMAdapter;
import com.example.projectver3.giaodich.ExpenseActivity;
import com.example.projectver3.login.LoginActivity;
import com.example.projectver3.model.DanhMuc;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CateFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPagerDM;

    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference data_DM = database.getReference("DanhMuc");
    ;
    public static ArrayList<DanhMuc> danhMucList = new ArrayList<>();
    public static final int REQUEST_CODE_ADD_UPDATE_TRANSACTION = 1; // Định nghĩa request code

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cate, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPagerDM = view.findViewById(R.id.view_pagerDM);
        ViewPageDMAdapter viewPageAdapter = new ViewPageDMAdapter(getActivity());
        viewPagerDM.setAdapter(viewPageAdapter);
        new TabLayoutMediator(tabLayout, viewPagerDM, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText(getString(R.string.nav_cp));
                        break;
                    case 1:
                        tab.setText(getString(R.string.nav_tn));
                        break;
                }
            }
        }).attach();
        DocDLDM();
        ExpenseActivity.docDLDMGD(LoginActivity.currenEmailUser);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_UPDATE_TRANSACTION && resultCode == RESULT_OK) {
            ArrayList<DanhMuc> updatedDanhMucList = data.getParcelableArrayListExtra("updatedDanhMucList");
            if (updatedDanhMucList != null) {
                danhMucList = updatedDanhMucList;
                // Cập nhật RecyclerView hoặc bất kỳ xử lý nào bạn cần
            }
        }
    }

        public static void DocDLDM(DanhMucAdapter adapter) {

        ArrayList<DanhMuc> ex = new ArrayList<>();
        ArrayList<DanhMuc> in = new ArrayList<>();

        data_DM.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                danhMucList.clear();
                //Toast.makeText(context, "thay đổi", Toast.LENGTH_SHORT).show();
                for (DataSnapshot item : snapshot.getChildren()) {
                    DanhMuc danhMucFB = item.getValue(DanhMuc.class);
                    if (danhMucFB.getMail().equals(LoginActivity.currenEmailUser)){
                        danhMucList.add(danhMucFB);

                        Log.d("danhMucList", danhMucList.toString());
                        Log.d("danhMucListCount", String.valueOf(danhMucList.size()));
                    }
                }

                for (DanhMuc item : danhMucList) {
                    if (item.isLoai()) {
                        in.add(item);
                    } else {
                        ex.add(item);
                    }
                }
//                adapter.notifyDataSetChanged();
                adapter.updateData(ex, in);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static void DocDLDM() {
        data_DM.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                danhMucList.clear();
                //Toast.makeText(context, "thay đổi", Toast.LENGTH_SHORT).show();
                for (DataSnapshot item : snapshot.getChildren()) {
                    DanhMuc danhMucFB = item.getValue(DanhMuc.class);
                    if (danhMucFB.getMail().equals(LoginActivity.currenEmailUser)) {
                        danhMucList.add(danhMucFB);

                        Log.d("danhMucList", danhMucList.toString());
                        Log.d("danhMucListCount", String.valueOf(danhMucList.size()));
                    }
                }

                for (DanhMuc item : danhMucList) {
                    if (item.isLoai()) {
                        dsIn.add(item);
                    } else {
                        dsEx.add(item);
                    }
                }
//            adapterEx.updateData();
//            adapterIn.updateData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
