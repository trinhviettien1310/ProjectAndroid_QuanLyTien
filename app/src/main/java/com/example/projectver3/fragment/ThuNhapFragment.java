package com.example.projectver3.fragment;


import static com.example.projectver3.HomeActivity.thongKeTN;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectver3.DanhSachGiaoDichActivity;
import com.example.projectver3.R;
import com.example.projectver3.adapter.ThongKeAdapter;
import com.example.projectver3.giaodich.ExpenseActivity;
import com.example.projectver3.model.ThongKe;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ThuNhapFragment extends Fragment implements OnChartValueSelectedListener {
    PieChart pieThuNhap;

    private RecyclerView rcvThuNhap;

    //private ArrayList<DanhMuc> listThuNhap = new ArrayList<>();

    private ThongKeAdapter adapter;

    private FloatingActionButton btnAdd;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String url = "http://192.168.138.174/androidwebservice/danhmuc.php";
    public ThuNhapFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    public static ChiPhiFragment newInstance(String param1, String param2) {
        ChiPhiFragment fragment = new ChiPhiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thu_nhap, container, false);
        pieThuNhap = view.findViewById(R.id.pieThuNhap);
        rcvThuNhap = view.findViewById(R.id.rcvThuNhap);
        btnAdd = view.findViewById(R.id.btnThuNhap);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ExpenseActivity.class);
                startActivity(intent);
            }
        });



        pieThuNhap.setRotationEnabled(true);
        pieThuNhap.setDescription(new Description());
        pieThuNhap.setHoleRadius(35f);
        pieThuNhap.setTransparentCircleAlpha(0);
        pieThuNhap.setCenterText("PieChart");
        pieThuNhap.setCenterTextSize(20);
        pieThuNhap.animateY(1000);
        pieThuNhap.setDrawEntryLabels(true);
        pieThuNhap.setOnChartValueSelectedListener(this);

        //
        //rcv
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getLayoutInflater().getContext());
        rcvThuNhap.setLayoutManager(linearLayoutManager);
        adapter = new ThongKeAdapter(thongKeTN, getContext(), new ThongKeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ThongKe thongKe) {
                Intent intent = new Intent(getContext(), DanhSachGiaoDichActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("list_gd", thongKe);
                intent.putExtras(bundle);
                startActivityForResult(intent, ChiPhiFragment.MY_REQUEST_CODE);
            }
        });
        rcvThuNhap.setAdapter(adapter);
        addDataSet(pieThuNhap);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(getLayoutInflater().getContext(), "Value: "
                + e.getY()
                + ", index: "
                + h.getX()
                + ", DataSet index: "
                + h.getDataSetIndex(), Toast.LENGTH_SHORT).show();
    }

    private void addDataSet(PieChart pieChart) {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();
        ArrayList<Float> yData = new ArrayList<>();
        ArrayList<String> xData = new ArrayList<>();
        ArrayList<Integer> colors=new ArrayList<>();

        for (ThongKe data:thongKeTN
        ) {
            xData.add(data.getTenGD());
            yData.add(data.getTongTien());
            colors.add(Color.parseColor(data.getMau()));
        }
        Log.d("mau tn", colors.toString());

        for (int i = 0; i < yData.size();i++){
            yEntrys.add(new PieEntry(yData.get(i),i));
        }
        for (int i = 0; i < xData.size();i++){
            xEntrys.add(xData.get(i));
        }

        PieDataSet pieDataSet = new PieDataSet(yEntrys,"Thu Nhap");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);


        pieDataSet.setColors(colors);

        Legend legend= pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);

        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    @Override
    public void onNothingSelected() {

    }
}