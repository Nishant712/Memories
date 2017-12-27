package com.example.newapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Nishant on 10/25/2017.
 */

public class SecondQuestionAdapter extends FragmentPagerAdapter {
    public SecondQuestionAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int arg0) {
        switch(arg0) {
            case 0:
                return new SecondQuestionFragmentOne();
            case 1:
                return new SecondQuestionFragmentTwo();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
