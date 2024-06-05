package com.example.projectver3.fragment;

//import static com.example.projectver3.MainActivity.getData;

import static com.example.projectver3.HomeActivity.thongKeCP;

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


public class ChiPhiFragment extends Fragment implements OnChartValueSelectedListener {
    static final int MY_REQUEST_CODE = 10;
    private PieChart pieChiPhi;

    private RecyclerView rcvChiPhi;

    private ThongKeAdapter adapter;

    private FloatingActionButton btnAdd;




    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public ChiPhiFragment() {
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
        View view = inflater.inflate(R.layout.fragment_chi_phi, container, false);
        pieChiPhi = view.findViewById(R.id.pieChiPhi);
        btnAdd = view.findViewById(R.id.btnAddCP);
        rcvChiPhi = view.findViewById(R.id.rcvChiPhi);

        pieChiPhi.setRotationEnabled(true);
        pieChiPhi.setDescription(new Description());
        pieChiPhi.setHoleRadius(35f);
        pieChiPhi.setTransparentCircleAlpha(0);
        pieChiPhi.setCenterText("PieChart");
        pieChiPhi.setCenterTextSize(20);
        pieChiPhi.animateY(1000);
        pieChiPhi.setDrawEntryLabels(true);
        pieChiPhi.setOnChartValueSelectedListener(this);

        //rcv

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getLayoutInflater().getContext());
        rcvChiPhi.setLayoutManager(linearLayoutManager);
        adapter = new ThongKeAdapter(thongKeCP, getContext(), new ThongKeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ThongKe thongKe) {
                Intent intent = new Intent(getContext(), DanhSachGiaoDichActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("list_gd", thongKe);
                intent.putExtras(bundle);
                startActivityForResult(intent, MY_REQUEST_CODE);
                //startActivity(intent);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ExpenseActivity.class);
                startActivity(intent);
            }
        });
        rcvChiPhi.setAdapter(adapter);
        addDataSet(pieChiPhi);
        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //

    }



    private void addDataSet(PieChart pieChart) {

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();
        ArrayList<String> xData = new ArrayList<>();
        ArrayList<Float> yData = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        for (ThongKe data:thongKeCP
        ) {
            xData.add(data.getTenGD());
            yData.add(data.getTongTien());
            colors.add(Color.parseColor(data.getMau().toString()));
            Log.d("mau", data.getMau());
            Log.d("mau cp", colors.toString());
        }


//        for (int d = 0; d < danhMucMap.size(); d++) {
//            xData.add(danhMucMap.);
//            yData.add(data.get(d).getSoTien());
//
//        }
        for (int i = 0; i < yData.size();i++){
            yEntrys.add(new PieEntry(yData.get(i),i));
        }
        for (int i = 0; i < xData.size();i++){
            xEntrys.add(xData.get(i));
        }

        PieDataSet pieDataSet = new PieDataSet(yEntrys,"Chi Phi");
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
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(getLayoutInflater().getContext(), "Value: "
                + e.getY()
                + ", index: "
                + h.getX()
                + ", DataSet index: "
                + h.getDataSetIndex(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }
}