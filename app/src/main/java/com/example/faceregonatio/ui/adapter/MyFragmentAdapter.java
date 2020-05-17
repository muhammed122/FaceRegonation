package com.example.faceregonatio.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.faceregonatio.ui.fragment.profile.Profile;
import com.example.faceregonatio.ui.fragment.recognize.Recognize;

public class MyFragmentAdapter extends FragmentPagerAdapter {

   private int pageCount;

    public MyFragmentAdapter(@NonNull FragmentManager fm, int behavior , int pageCount) {
        super(fm, behavior);
        this.pageCount=pageCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       switch (position){
           case 0:
               return new Recognize();
           case 1:
               return new Profile();
             default:
                 return null;
       }
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
