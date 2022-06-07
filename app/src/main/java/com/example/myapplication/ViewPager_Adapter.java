package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.Fragments.AccountFragment;
import com.example.myapplication.Fragments.HistoryFragment;
import com.example.myapplication.Fragments.HomeFragment;
import com.example.myapplication.Fragments.SearchFragment;

public class ViewPager_Adapter extends FragmentPagerAdapter {

   Context context;
   int totalTabs;
   String username,patientName;

    public ViewPager_Adapter(@NonNull FragmentManager fm,Context ctx,int totalTabs,String username,String patientName) {
        super(fm);
        this.context=context;
        this.totalTabs=totalTabs;
        this.username=username;
        this.patientName= patientName;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       switch(position){
           case 0:
               HomeFragment homeFragment=new HomeFragment();
               Bundle bundle = new Bundle();
               bundle.putString("username",username);
               bundle.putString("PatientName",patientName);
               homeFragment.setArguments(bundle);
               return homeFragment;
           case 1:
               SearchFragment searchFragment = new SearchFragment();
               Bundle bundles = new Bundle();
               bundles.putString("username",username);
               bundles.putString("PatientName",patientName);
               searchFragment.setArguments(bundles);
               return searchFragment;
           case 2:
               HistoryFragment historyFragment = new HistoryFragment();
               Bundle extra = new Bundle();
               extra.putString("username",username);
               historyFragment.setArguments(extra);
               return historyFragment;

           case 3:
               AccountFragment accountFragment = new AccountFragment();
               Bundle extras = new Bundle();
               extras.putString("username",username);
               accountFragment.setArguments(extras);

               return accountFragment;
       }
       return null;
    }

    @Override
    public int getCount() {
       return totalTabs;
    }


}
