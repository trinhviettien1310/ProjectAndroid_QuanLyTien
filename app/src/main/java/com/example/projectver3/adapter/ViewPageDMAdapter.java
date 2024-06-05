package com.example.projectver3.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.projectver3.fragment.DanhMucExpenseFragment;
import com.example.projectver3.fragment.DanhMucIncomeFragment;
import com.example.projectver3.model.DanhMuc;

import java.util.ArrayList;
import java.util.List;

public class ViewPageDMAdapter extends FragmentStateAdapter {

    private ArrayList<DanhMuc> danhMucList;
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    public ViewPageDMAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new DanhMucExpenseFragment();
            case 1:
                return new DanhMucIncomeFragment();
            default:
                return new DanhMucExpenseFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    // Rest of your code

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }

//    @NonNull
//    @Override
//    public Fragment getItem(int position) {
//        switch (position) {
//            case 0:
//                DanhMucExpenseFragment expenseFragment = new DanhMucExpenseFragment();
//                Bundle expenseBundle = new Bundle();
//                expenseBundle.putParcelableArrayList("danhMucList", danhMucList);
//                expenseFragment.setArguments(expenseBundle);
//                return expenseFragment;
//            case 1:
//                DanhMucIncomeFragment incomeFragment = new DanhMucIncomeFragment();
//                Bundle incomeBundle = new Bundle();
//                incomeBundle.putParcelableArrayList("danhMucList", danhMucList);
//                incomeFragment.setArguments(incomeBundle);
//                return incomeFragment;
//            default:
//                return null;
//        }
//    }

}
