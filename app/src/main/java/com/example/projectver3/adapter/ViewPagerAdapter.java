package com.example.projectver3.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.projectver3.fragment.ChiPhiFragment;
import com.example.projectver3.fragment.ThuNhapFragment;


public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ChiPhiFragment();
            case 1:
                return new ThuNhapFragment();
            default:
                return new ChiPhiFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
