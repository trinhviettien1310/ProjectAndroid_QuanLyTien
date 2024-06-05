package com.example.projectver3.fragment;


import static com.example.projectver3.fragment.CateFragment.DocDLDM;
import static com.example.projectver3.fragment.CateFragment.danhMucList;
import static com.example.projectver3.fragment.CateFragment.data_DM;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectver3.AddUpdateDanhMucActivity;
import com.example.projectver3.R;
import com.example.projectver3.adapter.DanhMucAdapter;
import com.example.projectver3.adapter.SwipeAndDragHelper;
import com.example.projectver3.giaodich.GridSpacingItemDecoration;
import com.example.projectver3.model.DanhMuc;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;


public class DanhMucExpenseFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static DanhMucAdapter adapterEx;


    public DanhMucExpenseFragment() {
        // Required empty public constructor
    }


    public static DanhMucExpenseFragment newInstance(String param1, String param2) {
        DanhMucExpenseFragment fragment = new DanhMucExpenseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_danhmuc, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.revDanhMucExpense);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 5);
        recyclerView.setLayoutManager(layoutManager);
        int spanCount = 5; // 3 columns
        int spacing = 50; // 50px
        boolean includeEdge = true;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        FloatingActionButton btnThemDanhmucExpense = view.findViewById(R.id.btnThemDanhmucExpense);


        // Tạo danh sách danh mục chi tiêu
        List<DanhMuc> danhMucChiTieu = new ArrayList<>();
        for (DanhMuc danhMuc : danhMucList) {
            if (!danhMuc.isLoai()) {
                danhMucChiTieu.add(danhMuc);
            }
        }

        adapterEx = new DanhMucAdapter(danhMucChiTieu, requireContext(), false);
        recyclerView.setAdapter(adapterEx);

//        RelativeLayout layout = view.findViewById(R.id.rlFragEx);
        SwipeAndDragHelper swipeAndDragHelper = new SwipeAndDragHelper(adapterEx);
        ItemTouchHelper touchHelper = new ItemTouchHelper(swipeAndDragHelper);
        adapterEx.setTouchHelper(touchHelper);
        recyclerView.setAdapter(adapterEx);
        touchHelper.attachToRecyclerView(recyclerView);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                DocDLDM();
            }
        }, 1000);


        adapterEx.notifyDataSetChanged();
        data_DM.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DocDLDM(adapterEx);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DocDLDM(adapterEx);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                DocDLDM(adapterEx);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnThemDanhmucExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch AddUpdateTransactionActivity
                Intent intent = new Intent(getActivity(), AddUpdateDanhMucActivity.class);
                intent.putExtra("isEditMode", false); // Đây là chế độ thêm mới
                startActivity(intent);
            }
        });


        return view;
    }


}