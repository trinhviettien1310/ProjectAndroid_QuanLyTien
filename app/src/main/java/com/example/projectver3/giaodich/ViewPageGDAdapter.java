package com.example.projectver3.giaodich;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPageGDAdapter extends FragmentStatePagerAdapter {
    public ViewPageGDAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ExpenseFragment();
            case 1:
                return new IncomeFragment();
            default:
                return new ExpenseFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title= "";
        switch (position){
            case 0:
                title = "Chi Phí";
                break;
            case 1:
                title = "Thu Nhập";
        }
        return title;
    }
}
