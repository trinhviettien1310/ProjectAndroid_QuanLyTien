package com.example.projectver3;



import static com.example.projectver3.fragment.CateFragment.DocDLDM;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectver3.fragment.AcountFragment;
import com.example.projectver3.fragment.CateFragment;
import com.example.projectver3.fragment.HomeFragment;
import com.example.projectver3.fragment.LichFragment;
import com.example.projectver3.fragment.RemindFragment;
import com.example.projectver3.giaodich.ExpenseActivity;
import com.example.projectver3.login.LoginActivity;
import com.example.projectver3.login.MainActivity;
import com.example.projectver3.model.DanhMuc;
import com.example.projectver3.model.GiaoDich;
import com.example.projectver3.model.ThongKe;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    TextView tvEmail;
    ImageView tvHinhAnh;

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_ACOUNT = 1;
    private static final int FRAGMENT_CATE = 2;
    private static final int FRAGMENT_REMIND = 3;

    private static final int FRAGMENT_LICH = 4;
    private int mCurrentFragment = FRAGMENT_HOME;
    DrawerLayout mDrawerLayout;
    Toolbar toolbar;

    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    public static Map<String, Float> chiPhiMap = new HashMap<>();
    public static Map<String, Float> thuNhapMap = new HashMap<>();
    public static ArrayList<ThongKe> data = new ArrayList<>();


    public static ArrayList<GiaoDich> listGD = new ArrayList<>();
    public static ArrayList<DanhMuc> danhMucList = new ArrayList<>();
    public static ArrayList<ThongKe> thongKeCP = new ArrayList<>();
    public static ArrayList<ThongKe> thongKeTN = new ArrayList<>();

    public static Set thongKeChi;

    public static Set thongKeThu;
    public static ArrayList<GiaoDich> listChiPhi = new ArrayList<>();

    public static ArrayList<GiaoDich> listThuNhap = new ArrayList<>();

    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference data_giaoDich = database.getReference("GiaoDich");;

    ActionBar actionBar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setControl();
        onClickPushData();
        DocDL();
        //getData();
        setEvent();

    }

    private void setEvent() {
        ExpenseActivity.docDLTK();
        ExpenseActivity.docDLDMGD(LoginActivity.currenEmailUser);
//        Log.d("email", LoginActivity.currenEmailUser);
        if (tvEmail != null){
            tvEmail.setText("LoginActivity.currenEmailUser");
        }

        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //
        navigationView.setNavigationItemSelectedListener(this);
        //
        actionBar = getSupportActionBar();
        actionBar.setTitle("Thống Kê");
        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_chart).setChecked(true);
        data_giaoDich.addChildEventListener(new ChildEventListener() {
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
                DocDL();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public static void  getList() {
        listChiPhi.clear();
        //Log.d("check list true", list.toString());
        for (int i = 0; i < listGD.size(); i++) {
            if (listGD.get(i).getDanhMuc().isLoai()){
                //Log.d("check list true", listGD.get(i).toString());
                listThuNhap.add(listGD.get(i));
            }
            else {
                listChiPhi.add(listGD.get(i));
            }
        }
        //Log.d("check list chi phi", listChiPhi.toString());
    }
    public static void  getDataThongKe(){
        thongKeCP.clear();
        thongKeTN.clear();
        //chi phi
        for (Map.Entry<String, Float> entry : chiPhiMap.entrySet()) {
            for (int i = 0; i < listGD.size(); i++) {
                if (entry.getKey().equals(listGD.get(i).getDanhMuc().getTenDanhMuc())){
                    ThongKe data = new ThongKe(entry.getKey(),
                            listGD.get(i).getDanhMuc().getHinh(),
                            listGD.get(i).getDanhMuc().getMau(),
                            entry.getValue());
                    //Log.d("Thong ke ", data.toString());
                    thongKeCP.add(data);
                    break;
                }
            }
        }
        thongKeChi = thongKeCP.stream()
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(ThongKe::getTenGD))));

        //thu nhap
        for (Map.Entry<String, Float> entry : thuNhapMap.entrySet()) {
            for (int i = 0; i < listGD.size(); i++) {
                if (entry.getKey().equals(listGD.get(i).getDanhMuc().getTenDanhMuc())){
                    ThongKe data = new ThongKe(entry.getKey(),
                            listGD.get(i).getDanhMuc().getHinh(),
                            listGD.get(i).getDanhMuc().getMau(),
                            entry.getValue());
                    //Log.d("Thong ke ", data.toString());
                    thongKeTN.add(data);
                    break;
                }
            }
        }
        thongKeThu = thongKeTN.stream()
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(ThongKe::getTenGD))));
        //Log.d("Thong ke thu nhap", thongKeTN.toString());
    }

    public static void dataHashMap(){
        chiPhiMap.clear();
        thuNhapMap.clear();
        for (int i = 0; i < listGD.size(); i++) {
            if (!listGD.get(i).getDanhMuc().isLoai()){
                String ten = listGD.get(i).getDanhMuc().getTenDanhMuc();
                float tong = Float.valueOf(listGD.get(i).getSoTien());
                if (chiPhiMap.containsKey(ten)){
                    float tongTien =  chiPhiMap.get(ten).floatValue();
                    tongTien += tong;
                    chiPhiMap.put(ten, tongTien);
                }else {
                    chiPhiMap.put(ten, tong);
                }
            }
            else {
                String ten = listGD.get(i).getDanhMuc().getTenDanhMuc();
                float tong = Float.valueOf(listGD.get(i).getSoTien());
                if (thuNhapMap.containsKey(ten)){
                    float tongTien =  thuNhapMap.get(ten).floatValue();
                    tongTien += tong;
                    thuNhapMap.put(ten, tongTien);
                }else {
                    thuNhapMap.put(ten, tong);
                }
            }

        }
    }
    public static void onClickPushData(){
//        GiaoDich giaoDich11 = new GiaoDich("11", "2023/10/22","8000", new DanhMuc("7", "danh muc 7", "#1b3a57", "img_7", "demo5ad", true));
//        //Log.d("hien thi gd", giaoDich3.toString());
//        data_giaoDich.child(giaoDich11.getMaGiaoDich()).setValue(giaoDich11);
        //Log.d("them gd", "");
    }

    public static void DocDL(){
        data_giaoDich.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listGD.clear();
                //Log.d("tesst doc dl", "fafdasd");
                for (DataSnapshot item : snapshot.getChildren()) {
                    GiaoDich giaoDich = item.getValue(GiaoDich.class);
                    if (giaoDich.getDanhMuc().getMail().equals(LoginActivity.currenEmailUser)){
                        //Log.d("test giao dich", giaoDich.toString());
                        listGD.add(giaoDich);
                    }

                }

                getList();
                //Log.d("test list", listChiPhi.toString());
                dataHashMap();
                getDataThongKe();
                //Log.d("Thong ke ds data", thongKe.toString());
                //Log.d("hash map", danhMucMap.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void setControl() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navView);

        tvHinhAnh = findViewById(R.id.ivHinhAnh);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_chart){
            if (mCurrentFragment != FRAGMENT_HOME){
                replaceFragment(new HomeFragment());
                actionBar.setTitle("Thống Kê");
                mCurrentFragment = FRAGMENT_HOME;
            }
        } else if (id == R.id.nava_acount) {
            if (mCurrentFragment != FRAGMENT_ACOUNT){
                replaceFragment(new AcountFragment());
                actionBar.setTitle("Tài Khoản");
                mCurrentFragment = FRAGMENT_ACOUNT;
            }
        }else if (id == R.id.nava_cate) {

            if (mCurrentFragment != FRAGMENT_CATE){
                DocDLDM();
                replaceFragment(new CateFragment());
                actionBar.setTitle("Danh Mục");
                mCurrentFragment = FRAGMENT_CATE;

            }
        }
        else if (id == R.id.nava_remine) {
            if (mCurrentFragment != FRAGMENT_REMIND){
                replaceFragment(new RemindFragment());
                actionBar.setTitle("Nhắc Nhở");
                mCurrentFragment = FRAGMENT_REMIND;
            }
        }

        else if (id == R.id.nava_cal) {
            if (mCurrentFragment != FRAGMENT_LICH){
                replaceFragment(new LichFragment());
                actionBar.setTitle("Lịch");
                mCurrentFragment = FRAGMENT_LICH;
            }
        }
        else if (id == R.id.nava_louout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent  =  new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

}